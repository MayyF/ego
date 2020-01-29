package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:S
 * @Date:20/1/29
 */
public interface TbUserService {
    /**
     * 登录
     * @param user * @return
     */
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据 token 查询用户信息 * @param token
     *
     */
    public EgoResult getUserInfoByToken(String token);

    /**
     * 退出
     */
    public EgoResult logout(String token, HttpServletRequest request,HttpServletResponse response);

}
