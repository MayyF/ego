package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

/**
 * @Auther:S
 * @Date:19/7/24
 */
public interface TbContentService {

    EasyUIDataGrid showContent(long categoryId, int paeg, int rows);

    int save(TbContent tbContent);
}
