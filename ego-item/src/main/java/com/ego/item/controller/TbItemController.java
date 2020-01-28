package com.ego.item.controller;

import com.ego.item.service.TbItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Auther:S
 * @Date:20/1/26
 */

@Controller
public class TbItemController {

    @Resource
    private TbItemService tbItemServiceImpl;

    @RequestMapping("item/{id}.html")
    public String showItemDetail(@PathVariable("id") long id, Model model){
        model.addAttribute("item",tbItemServiceImpl.show(id));
        return "item";
    }
}
