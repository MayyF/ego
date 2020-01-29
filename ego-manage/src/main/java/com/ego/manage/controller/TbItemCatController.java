package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.manage.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/6/14
 */

@Controller
public class TbItemCatController {

    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasyUiTree>showCat(@RequestParam(defaultValue = "0") long id){
        System.out.println(tbItemCatServiceImpl);
        List<EasyUiTree>easyUiTrees=tbItemCatServiceImpl.show(id);
        System.out.println(easyUiTrees);
        return  easyUiTrees;
    }


}
