package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Auther:S
 * @Date:19/6/14
 */
public interface TbItemCatDubboService {

    List<TbItemCat>show(long pid);

    TbItemCat selById(long id);

}
