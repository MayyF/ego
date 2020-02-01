package com.ego.order.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther:S
 * @Date:20/1/31
 */
public interface TbOrderService {

    List<TbItemChild> showOrderCart(List<Long>ids, HttpServletRequest request);

    EgoResult create(MyOrderParam param,HttpServletRequest request);
}
