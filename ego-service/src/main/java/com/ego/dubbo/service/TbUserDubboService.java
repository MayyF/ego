package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @Auther:S
 * @Date:20/1/29
 */
public interface TbUserDubboService {

    /**
     * 根据用户名和密码查询登录
     * @param user
     * @return
     */
    public TbUser selByUser(TbUser user);

}
