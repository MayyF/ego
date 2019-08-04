package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/24
 */
public interface TbContentService {

    EasyUIDataGrid showContent(long categoryId, int paeg, int rows);

    int save(TbContent tbContent);


}
