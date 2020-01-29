package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:S
 * @Date:20/1/29
 */

@Controller
public class TbUserController {

    @Resource
    private TbUserService tbUserServiceImpl;

    @RequestMapping("user/showLogin")
    public String showLogin(@RequestHeader("Referer") String url, Model model){
        model.addAttribute("redirect",url);
        return "login";
    }



    /**
    * 登录
    * @param user * @return
    */
    @RequestMapping("user/login")
    @ResponseBody
    public EgoResult login(TbUser user, HttpServletRequest req, HttpServletResponse resp){
        return tbUserServiceImpl.login(user,req,resp);
    }

    /**
     * 通过token获取用户信息
     * @param token
     * @param callback
     * @return
     */

    @RequestMapping("user/token/{token}")
    @ResponseBody
    public Object getUserInfo(@PathVariable("token") String token, String callback){
        EgoResult er=tbUserServiceImpl.getUserInfoByToken(token);
        if(callback!=null&&!callback.isEmpty()){
            MappingJacksonValue mjk=new MappingJacksonValue(er);
            mjk.setJsonpFunction(callback);
            return mjk;
        }
        return er;
    }

    /**
     * 退出
     * @param token
     * @param callback
     * @return
     */

    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable("token") String token,String callback,HttpServletRequest request,HttpServletResponse response){
        EgoResult er=tbUserServiceImpl.logout(token,request,response);
        if(callback!=null&&!callback.isEmpty()){
            MappingJacksonValue mjv=new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }
        return er;
    }


}
