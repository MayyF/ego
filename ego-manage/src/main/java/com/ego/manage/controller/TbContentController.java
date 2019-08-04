package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:S
 * @Date:19/7/24
 */

@Controller
@RequestMapping("/content")
public class TbContentController {

    @Resource
    private TbContentService tbContentServiceImpl;

    @ResponseBody
    @RequestMapping("/query/list")
    public EasyUIDataGrid showContent(long categoryId, int page, int rows){
        return tbContentServiceImpl.showContent(categoryId,page,rows);
    }

    @RequestMapping("/save")
    @ResponseBody
    public EgoResult save(TbContent content){
        EgoResult er=new EgoResult();
        int index=tbContentServiceImpl.save(content);
        if(index>0){
            er.setStatus(200);
        }
        return er;
    }



}
