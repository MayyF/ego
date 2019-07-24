package com.ego.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/22
 */
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    public List<TbContentCategory> selByPid(long id) {
        TbContentCategoryExample example=new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    public int insTbContentCategory(TbContentCategory category) {
        return tbContentCategoryMapper.insertSelective(category);
    }

    @Override
    public int updIsParentById(TbContentCategory category) {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public TbContentCategory selById(long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }
}
