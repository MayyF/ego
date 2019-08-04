package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:S
 * @Date:19/7/14
 */

@Controller
public class PageController {

    @RequestMapping("/")
    public String index(){
        return "forward:/showBigPic";
    }

}
