package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @Auther:S
 * @Date:20/1/26
 */
public interface TbItemParamItemDubboSerice {
    /**
     * 根据商品 id 查询商品规格参数 * @param itemId
     * @return
     */
    TbItemParamItem selByItemId(long itemId);


}

