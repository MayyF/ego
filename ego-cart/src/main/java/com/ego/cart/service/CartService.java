package com.ego.cart.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther:S
 * @Date:20/1/30
 */
public interface CartService {

    /**add to the cart
     *@param id
     *@param num :
     *@param request :
     *@return : void
     *
     */
    public void addCart(long id, int num, HttpServletRequest request);

    /**show detail of cart
     * *@param request :
     *@return : java.util.List<com.ego.commons.pojo.TbItemChild>
     *
     */
    public List<TbItemChild> showCart(HttpServletRequest request);


    /**show numbers of staff by id
     *@param id :
     *@param num :
     *@param request :
     *@return : com.ego.commons.pojo.EgoResult
     *
     */
    public EgoResult update(long id, int num, HttpServletRequest request);


    /**delete staff from cart
     *@param id :
     *@param request :
     *@return : com.ego.commons.pojo.EgoResult
     *
     */
    public EgoResult delete(long id, HttpServletRequest request);
}
