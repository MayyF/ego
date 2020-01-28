package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;

import javax.annotation.Resource;

/**
 * @Auther:S
 * @Date:20/1/25
 */
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public int insDes(TbItemDesc tbItemDesc) {
        return tbItemDescMapper.insert(tbItemDesc);
    }

    @Override
    public TbItemDesc selByItemid(long itemid) {
        return tbItemDescMapper.selectByPrimaryKey(itemid);
    }
}
