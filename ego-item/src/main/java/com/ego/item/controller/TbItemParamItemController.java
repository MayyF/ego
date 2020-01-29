package com.ego.item.controller;

import com.ego.item.service.TbItemParamItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public String param(@PathVariable("id") long id){
        id=48L;
        String res=tbItemParamItemServiceImpl.showParam(id);
        System.out.println(res);
        return res;
    }

}
