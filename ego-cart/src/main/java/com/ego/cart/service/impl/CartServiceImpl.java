package com.ego.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.redis.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther:S
 * @Date:20/1/30
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private JedisDao jedisDaoImpl;

    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;

    @Value("${passport.url}")
    private String passportUrl;

    @Value("${cart.key}")
    private String cartKey;

    @Value("${token.key}")
    private String tokenKey;

    @Override
    public void addCart(long id, int num, HttpServletRequest request) {
        List<TbItemChild>list=new ArrayList<>();
        String token= CookieUtils.getCookieValue(request,tokenKey);
        String jsonUser= HttpClientUtil.doPost(passportUrl+token);
        EgoResult er= JsonUtils.jsonToPojo(jsonUser,EgoResult.class);
        String key=cartKey+((LinkedHashMap)er.getData()).get("username");
        if(jedisDaoImpl.exists(key)){
            String json=jedisDaoImpl.get(key);
            if(json!=null&&!json.isEmpty()){
                list=JsonUtils.JsonToList(json,TbItemChild.class);
                for (TbItemChild td:list){
                    if(td.getId()==id){
                        td.setNum(td.getNum()+num);
                        jedisDaoImpl.set(key,JsonUtils.objectToJson(list));
                        return;
                    }
                }
            }
        }

        TbItem item=tbItemDubboServiceImpl.selById(id);
        TbItemChild td=new TbItemChild();
        td.setId(item.getId());
        td.setImages(item.getImage()==null||item.getImage().isEmpty()?new String[1]:item.getImage().split(","));
        td.setTitle(item.getTitle());
        td.setNum(num);
        td.setPrice(item.getPrice());
        list.add(td);
        jedisDaoImpl.set(key,JsonUtils.objectToJson(list));

    }

    @Override
    public List<TbItemChild> showCart(HttpServletRequest request) {
        String token=CookieUtils.getCookieValue(request,tokenKey);
        String jsonUser=HttpClientUtil.doPost(passportUrl+token);
        EgoResult er=JsonUtils.jsonToPojo(jsonUser,EgoResult.class);
        String key=cartKey+((LinkedHashMap)er.getData()).get("username");
        String json=jedisDaoImpl.get(key);
        return JsonUtils.JsonToList(json,TbItemChild.class);
    }

    @Override
    public EgoResult update(long id, int num, HttpServletRequest request) {
        String token=CookieUtils.getCookieValue(request,tokenKey);
        String jsonUser=HttpClientUtil.doPost(passportUrl+token);
        EgoResult er=JsonUtils.jsonToPojo(jsonUser,EgoResult.class);
        String key=cartKey+((LinkedHashMap)er.getData()).get("username");
        String json=jedisDaoImpl.get(key);
        List<TbItemChild>list=JsonUtils.JsonToList(json,TbItemChild.class);
        EgoResult et=new EgoResult();
        for (TbItemChild td:list){
            if(td.getId()==id){
                td.setNum(num);
                String ok=jedisDaoImpl.set(key,JsonUtils.objectToJson(list));
                if(ok.equals("OK")){
                    et.setStatus(200);
                }
            }
        }
        return  et;
    }

    @Override
    public EgoResult delete(long id, HttpServletRequest request) {
        String token= CookieUtils.getCookieValue(request,tokenKey);
        String jsonUser=HttpClientUtil.doPost(passportUrl+token);
        EgoResult er=JsonUtils.jsonToPojo(jsonUser,EgoResult.class);
        String key=cartKey+((LinkedHashMap)er.getData()).get("username");
        String json=jedisDaoImpl.get(key);
        List<TbItemChild>list=JsonUtils.JsonToList(json,TbItemChild.class);
        Iterator<TbItemChild>itr=list.iterator();
        while (itr.hasNext()){
            if(itr.next().getId()==id){
                itr.remove();
            }
        }
        er=new EgoResult();
        String ok=jedisDaoImpl.set(key,JsonUtils.objectToJson(list));
        if(ok.equals("OK")){
            er.setStatus(200);
        }
        return er;
    }
}
