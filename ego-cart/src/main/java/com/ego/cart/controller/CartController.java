package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther:S
 * @Date:20/1/30
 */

@Controller
public class CartController {

    @Resource
    private CartService cartServiceImpl;

    @RequestMapping("cart/add/{id}.html")
    public String addCart(@PathVariable("id") long id, int num , HttpServletRequest request){
        cartServiceImpl.addCart(id,num,request);
        return "cartSuccess";
    }

    /**
     *@param model :
     *@param request :
     *@return : java.lang.String
     *
     */
    @RequestMapping("cart/cart.html")
    public String showCart(Model model,HttpServletRequest request){
        model.addAttribute("cartList",cartServiceImpl.showCart(request));
        return "cart";
    }

    /**modify the number of staff by id
     *@param id :
     *@param num :
     *@param request :
     *@return : com.ego.commons.pojo.EgoResult
     *
     */
    @RequestMapping("cart/update/num/{id}/{num}.action")
    @ResponseBody
    public EgoResult update(@PathVariable("id") long id, @PathVariable("num") int num, HttpServletRequest request){
        return cartServiceImpl.update(id,num,request);
    }

    @RequestMapping("cart/detele/{id}.html")
    @ResponseBody
    public EgoResult delete(@PathVariable("id") long id, HttpServletRequest request){
        return cartServiceImpl.delete(id,request);
    }

}
