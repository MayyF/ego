package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Auther:S
 * @Date:20/1/31
 */

@Service
public class TbOrderServiceImpl implements TbOrderService {

    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${cart.key}")
    private String cartKey;

    @Value("${passport.url}")
    private String passprtUrl;

    @Value("${token.key}")
    private String tokenKey;

    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;

    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;


    @Override
    public List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request) {
        String token= CookieUtils.getCookieValue(request,tokenKey);
        String url=String.format(passprtUrl,token);
        String rst= HttpClientUtil.doPost(url);
        EgoResult er= JsonUtils.jsonToPojo(rst,EgoResult.class);
        String key=cartKey+((LinkedHashMap)er.getData()).get("username");
        String json=jedisDaoImpl.get(key);
        List<TbItemChild>list=JsonUtils.JsonToList(json,TbItemChild.class);
        List<TbItemChild>lsNew=new ArrayList<>();
        for (TbItemChild cd:list){
            if(ids.contains(cd.getId())){
                //
                TbItem tbItem=tbItemDubboServiceImpl.selById(cd.getId());
                if(tbItem.getNum()>=cd.getNum()){
                    cd.setEnough(true);
                }else{
                    cd.setEnough(false);
                }
                lsNew.add(cd);
            }
        }
        return lsNew;
    }

    @Override
    public EgoResult create(MyOrderParam param, HttpServletRequest request) {
        TbOrder order=new TbOrder();
        order.setPayment(param.getPayment());
        order.setPaymentType(param.getPaymentType());
        long id= IDUtils.genItemId();
        order.setOrderId(id+"");
        Date date=new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        String token=CookieUtils.getCookieValue(request,tokenKey);
        String cmUrl=String.format(passprtUrl,token);
        String resJson=HttpClientUtil.doPost(cmUrl);
        EgoResult er=JsonUtils.jsonToPojo(resJson,EgoResult.class);
        Map user=(LinkedHashMap)er.getData();
        order.setUserId(Long.parseLong(user.get("id").toString()));
        order.setBuyerNick(user.get("username").toString());
        for (TbOrderItem item:param.getOrderItems()){
            item.setId(IDUtils.genItemId()+"");
            item.setOrderId(id+"");
        }
        TbOrderShipping shipping=param.getOrderShipping();
        shipping.setOrderId(id+"");
        shipping.setCreated(date);
        shipping.setUpdated(date);
        EgoResult erR=new EgoResult();
        try {
            int idx=tbOrderDubboServiceImpl.insOrder(order,param.getOrderItems(),shipping);
            if(idx>0){
                erR.setStatus(200);
                String json=jedisDaoImpl.get(cartKey+user.get("username"));
                List<TbItemChild>listCart=JsonUtils.JsonToList(json,TbItemChild.class);
                List<TbItemChild>newList=new ArrayList<>();
                for (TbItemChild child:listCart){
                    for (TbOrderItem item:param.getOrderItems()){
                        System.out.println("1:"+child.getId().longValue());
                        System.out.println("2:"+Long.parseLong(item.getItemId()));
                        if(child.getId().longValue()==Long.parseLong(item.getItemId())){
                            listCart.add(child);
                        }
                    }
                }
                for (TbItemChild myNew:newList){
                    listCart.remove(myNew);
                }
                jedisDaoImpl.set(cartKey+user.get("username"),JsonUtils.objectToJson(listCart));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return erR;
    }
}
