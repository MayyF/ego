package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:S
 * @Date:20/1/31
 */

public class TbOrderDubboServiceImpl implements TbOrderDubboService {

    @Resource
    private TbOrderMapper tbOrderMapper;
    @Resource
    private TbOrderItemMapper tbOrderItemMapper;
    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Override
    public int insOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping)throws Exception {
        int idx=tbOrderMapper.insertSelective(order);
        for(TbOrderItem tbOrderItem:list){
            idx+=tbOrderItemMapper.insertSelective(tbOrderItem);
        }
        idx+=tbOrderShippingMapper.insertSelective(shipping);
        if(idx==2+list.size()){
            return 1;
        }else{
            throw new Exception("create order fail");
        }

    }
}
