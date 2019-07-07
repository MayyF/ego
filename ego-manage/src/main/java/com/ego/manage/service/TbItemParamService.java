package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

/**
 * @Auther:S
 * @Date:19/7/7
 */
public interface TbItemParamService {
    EasyUIDataGrid showPage(int page,int row);

    int delete(String ids) throws Exception;

    EgoResult showParam(long catId);

    EgoResult save(TbItemParam param);
}
