package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther:S
 * @Date:19/7/22
 */

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

    @Override
    public List<EasyUiTree> showCategory(long id) {
        List<EasyUiTree>eutree=new ArrayList<>();
        List<TbContentCategory>list =tbContentCategoryDubboServiceImpl.selByPid(id);
        for(TbContentCategory item:list){
            EasyUiTree tree=new EasyUiTree();
            if(item.getStatus()==0)continue;
            tree.setId(item.getId());
            tree.setText(item.getName());
            tree.setState(item.getIsParent()?"closed":"open");
            eutree.add(tree);
        }
        return eutree;
    }

    @Override
    public EgoResult create(TbContentCategory category) {
        EgoResult er=new EgoResult();
        List<TbContentCategory>children=tbContentCategoryDubboServiceImpl.selByPid(category.getParentId());
        for (TbContentCategory item:children){
            if(item.getName().equals(category.getName())){
                er.setData("Already exist");
                return er;
            }
        }
        Date date=new Date();
        category.setCreated(date);
        category.setUpdated(date);
        category.setStatus(1);
        category.setSortOrder(1);
        category.setIsParent(false);
        long id= IDUtils.genItemId();
        int idx=tbContentCategoryDubboServiceImpl.insTbContentCategory(category);
        if(idx>0){
            TbContentCategory parent=new TbContentCategory();
            parent.setIsParent(true);
            parent.setId(category.getParentId());
            parent.setUpdated(date);
            tbContentCategoryDubboServiceImpl.updIsParentById(parent);
        }
        er.setStatus(200);
        Map<String,Long>map=new HashMap<>();
        map.put("id",id);
        er.setData(map);
        return er;
    }

    @Override
    public EgoResult update(TbContentCategory cate) {
        EgoResult er=new EgoResult();
        TbContentCategory cateSelect=tbContentCategoryDubboServiceImpl.selById(cate.getId());
        List<TbContentCategory>children=tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
        for(TbContentCategory item :children){
            if(item.getName().equals(cate.getName())){
                er.setData("Already exist");
                return er;
            }
        }
        int idx=tbContentCategoryDubboServiceImpl.updIsParentById(cate);
        if(idx>0){
            er.setStatus(200);
        }
        return er;
    }

    @Override
    public EgoResult delete(TbContentCategory category) {
        EgoResult er=new EgoResult();
        category.setStatus(0);
        int index=tbContentCategoryDubboServiceImpl.updIsParentById(category);
        if(index>0){
            TbContentCategory curr=tbContentCategoryDubboServiceImpl.selById(category.getId());
            List<TbContentCategory>list=tbContentCategoryDubboServiceImpl.selByPid(curr.getParentId());
            if(list==null||list.size()<=1) {
                TbContentCategory parent = new TbContentCategory();
                parent.setId(curr.getParentId());
                parent.setIsParent(false);
                int result = tbContentCategoryDubboServiceImpl.updIsParentById(parent);
                if (result > 0) {
                    er.setStatus(200);
                }
            }else{
                er.setStatus(200);
            }
        }
        return er;
    }
}
