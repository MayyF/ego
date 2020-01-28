package com.ego.item.controller;

import com.ego.item.service.TbItemParamItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Auther:S
 * @Date:20/1/27
 */

@Controller
public class TbItemParamItemController {

    @Resource
    private TbItemParamItemService tbItemParamItemServiceImpl;

    /**
     * 显示规格参数 * @param id
     * @return
     */

    @RequestMapping(value = "item/param/{id}.html",produces = "text/html;charset=utf-8")
    public String param(long id){
        return tbItemParamItemServiceImpl.showParam(id);
    }

}
