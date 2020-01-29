package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/22
 */
@Controller
@RequestMapping("/content/category")
public class TbContentCategoryController {
    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUiTree>showCategory(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryServiceImpl.showCategory(id);
    }

    @RequestMapping("/create")
    @ResponseBody
    public EgoResult create(TbContentCategory category){
        return tbContentCategoryServiceImpl.create(category);
    }

    @RequestMapping("/update")
    @ResponseBody
    public EgoResult update(TbContentCategory category){
        return tbContentCategoryServiceImpl.update(category);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public EgoResult delete(TbContentCategory category){
        return tbContentCategoryServiceImpl.delete(category);
    }
}
