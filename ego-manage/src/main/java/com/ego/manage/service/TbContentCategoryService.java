package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/22
 */
public interface TbContentCategoryService {
    List<EasyUiTree>showCategory(long id);

    EgoResult create(TbContentCategory category);

    EgoResult update(TbContentCategory cate);

    EgoResult delete(TbContentCategory category);
}
