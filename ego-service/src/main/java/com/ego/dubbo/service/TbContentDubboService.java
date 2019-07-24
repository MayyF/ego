package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

/**
 * @Auther:S
 * @Date:19/7/24
 */

public interface TbContentDubboService {
    EasyUIDataGrid selContentByPage(long categoryId, int page, int rows);

    int insContent(TbContent tbContent);

}
