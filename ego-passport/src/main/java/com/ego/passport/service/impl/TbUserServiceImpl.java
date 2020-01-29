package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.JedisDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Auther:S
 * @Date:20/1/29
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Reference
    private TbUserDubboService tbUserDubboServiceImpl;

    @Resource
    private JedisDao jedisDaoImpl;

    private String cookieName="TT_TOKEN";

    private int outTime=60*60*24*7;

    @Override
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er=new EgoResult();
        TbUser userSel=tbUserDubboServiceImpl.selByUser(user);
        if(userSel!=null){
            er.setStatus(200);
            String key= UUID.randomUUID().toString();
            jedisDaoImpl.set(key, JsonUtils.objectToJson(userSel));
            jedisDaoImpl.expire(key,outTime);
            CookieUtils.setCookie(request,response,cookieName,key,outTime);
        }else{
            er.setMsg("user or pwd error");
        }
        return er;
    }

    @Override
    public EgoResult getUserInfoByToken(String token) {
        EgoResult er=new EgoResult();
        String json=jedisDaoImpl.get(token);
        if(json!=null&&!json.isEmpty()){
            TbUser tbUser=JsonUtils.jsonToPojo(json,TbUser.class);
            tbUser.setPassword(null);
            er.setData(tbUser);
            er.setStatus(200);
            er.setMsg("OK");
        }else {
            er.setMsg("get error");
        }
        return er;
    }

    @Override
    public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        Long idx=jedisDaoImpl.del(token);
        CookieUtils.deleteCookie(request,response,cookieName);
        EgoResult er=new EgoResult();
        er.setStatus(200);
        er.setMsg("OK");
        return er;
    }
}
