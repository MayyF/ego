package com.ego.item.controller;

import com.ego.item.service.TbItemCatSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther:S
 * @Date:19/7/14
 */

@Controller
public class TbItemCatController {

    @Autowired
    private TbItemCatSerivce tbItemCatSerivceImpl;

    @RequestMapping("rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue showMenu(String callback){
        MappingJacksonValue mjv=new MappingJacksonValue(tbItemCatSerivceImpl.showCatMenu());
        mjv.setJsonpFunction(callback);
        return mjv;
    }
}
