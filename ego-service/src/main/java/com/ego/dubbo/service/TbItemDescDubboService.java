package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Auther:S
 * @Date:20/1/24
 */
public interface TbItemDescDubboService {


    int insDes(TbItemDesc tbItemDesc);

    /*
     * 根据主键查询商品描述对象
	 * @param itemid
	 * @return
     */
    TbItemDesc selByItemid(long itemid);
}
