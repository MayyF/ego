package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/7
 */

@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Reference
    private TbItemParamDubboService tbItemParamDubboService;

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Override
    public EasyUIDataGrid showPage(int page, int row) {

        EasyUIDataGrid dataGrid=tbItemParamDubboService.showPage(page,row);
        List<TbItemParam>list= (List<TbItemParam>) dataGrid.getRows();
        List<TbItemParamChild>children=new ArrayList<>();
        for(TbItemParam param:list){
            TbItemParamChild child=new TbItemParamChild();
            child.setCreated(param.getCreated());
            child.setId(param.getId());
            child.setItemCatId(param.getItemCatId());
            child.setParamData(param.getParamData());
            child.setUpdated(param.getUpdated());
            child.setItemCatName(tbItemCatDubboService.selById(child.getItemCatId()).getName());
            children.add(child);
        }
        dataGrid.setRows(children);
        return dataGrid;
    }

    @Override
    public int delete(String ids) throws Exception {
        return tbItemParamDubboService.delete(ids);
    }

    @Override
    public EgoResult showParam(long catId) {
        EgoResult er=new EgoResult();
        TbItemParam param=tbItemParamDubboService.selByCatId(catId);
        if(param!=null){
            er.setStatus(200);
            er.setData(param);
        }
        return er;
    }

    @Override
    public EgoResult save(TbItemParam param) {
        EgoResult er=new EgoResult();
        Date date=new Date();
        param.setCreated(date);
        param.setUpdated(date);
        int index=tbItemParamDubboService.insertParam(param);
        if(index>0){
            er.setStatus(200);
        }
        return er;
    }
}
