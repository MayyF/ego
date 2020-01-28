package com.ego.search.controller;

import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther:S
 * @Date:20/1/25
 */

@Controller
public class TbItemController {

    @Resource
    private TbItemService tbItemServiceImpl;

    @RequestMapping(value = "solr/init",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String init(){
        long start=System.currentTimeMillis();
        try {
            System.out.println("init ....");
            tbItemServiceImpl.init();
            long end=System.currentTimeMillis();
            return "初始化时间："+(end-start)/1000+"秒";
        } catch (Exception e) {
            e.printStackTrace();
            return "init error";
        }


    }

    /**
     * 搜索功能
     * @param model
     * @param q
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("search.html")
    public String search(ModelMap model, String q, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12") int rows){
        try {
            //q = new String(q.getBytes("iso-8859-1"),"utf-8");
            Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
            model.addAttribute("query", q);
            model.addAttribute("itemList", map.get("itemList"));
            model.addAttribute("totalPages", map.get("totalPages"));
            model.addAttribute("page", page);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";

    }

    @RequestMapping("test")
    public String test(Model model){
        String str="this is a test";
        model.addAttribute("test",str);
        return "test";
    }

    @RequestMapping("test1")
    public ModelAndView test1(){
        ModelAndView mmv=new ModelAndView("test");
        String str="this is a test";
        mmv.addObject("str",str);
        return mmv;
    }

    /**
     * 新增
     * @param map
     * @return
     */
    @RequestMapping("solr/add")
    @ResponseBody
    public int add(@RequestBody Map<String,Object>map){
        System.out.println("solr/add");
        System.out.println(map);
        System.out.println(map.get("item"));
        try {
            return tbItemServiceImpl.add((LinkedHashMap)map.get("item"),map.get("desc").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
