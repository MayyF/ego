package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:S
 * @Date:19/7/7
 */

@Controller
@RequestMapping("/item/param")
public class TbItemParamController {

    @Resource
    private TbItemParamService tbItemParamServiceImpl;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGrid showPage(int page,int rows){
        return tbItemParamServiceImpl.showPage(page,rows);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult er=new EgoResult();
        try {
            int index=tbItemParamServiceImpl.delete(ids);
            if(index==1){
                er.setStatus(200);
            }
        }catch (Exception e){
            er.setData(e.getMessage());
        }
        return er;
    }

    @RequestMapping("/query/itemcatid/{id}")
    @ResponseBody
    public EgoResult query(@PathVariable("id") long catId){
        return tbItemParamServiceImpl.showParam(catId);
    }

    @RequestMapping("/save/{catId}")
    @ResponseBody
    public EgoResult save(@PathVariable long catId, TbItemParam param){
        EgoResult er=null;
        param.setItemCatId(catId);
        er=tbItemParamServiceImpl.save(param);
        return er;
    }
}
