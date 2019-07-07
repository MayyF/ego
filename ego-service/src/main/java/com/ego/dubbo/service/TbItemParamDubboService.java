package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

/**
 * @Auther:S
 * @Date:19/6/20
 */
public interface TbItemParamDubboService {

    public EasyUIDataGrid showPage(int page,int rows);

    int delete(String ids) throws Exception;

    TbItemParam selByCatId(long id);

    int insertParam(TbItemParam param);
}
