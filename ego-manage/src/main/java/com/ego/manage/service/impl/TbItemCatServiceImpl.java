package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/6/14
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public List<EasyUiTree> show(long pid) {
        List<TbItemCat>list=tbItemCatDubboServiceImpl.show(pid);
        List<EasyUiTree>listTree=new ArrayList<>();
        for(TbItemCat cat:list){
            EasyUiTree tree=new EasyUiTree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent()?"closed":"open");
            listTree.add(tree);
        }
        return listTree;
    }
}
