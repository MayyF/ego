package com.ego.dubbo.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/22
 */

public interface TbContentCategoryDubboService {

    List<TbContentCategory>selByPid(long id);

    int insTbContentCategory(TbContentCategory category);

    int updIsParentById(TbContentCategory category);

    TbContentCategory selById(long id);
}
