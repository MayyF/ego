package com.ego.cart.interceptor;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:S
 * @Date:20/1/30
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final String COOKIEKEY="TT_TOKEN";
    private String reqUrl="http://localhost:8084/user/token/%s";
    private String reqDir="http://localhost:8084/user/showLogin?interurl=%s%3Fnum=%s";
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {

        String token= CookieUtils.getCookieValue(req,COOKIEKEY);
        //如果携带cookie则查询用户信息
        if(token!=null&&!token.equals("")){
            String url=String.format(reqUrl,token);
            System.out.println("interceptor url:"+url);
            String json= HttpClientUtil.doPostJson(url,COOKIEKEY);
            EgoResult er= JsonUtils.jsonToPojo(json,EgoResult.class);
            if(er.getStatus()==200){
                return true;
            }
        }
        //从哪来到哪里去
        String num=req.getParameter("num");
        String redirectUrl=String.format(reqDir,req.getRequestURL(),num);
        System.out.println("interceptor redirect url:"+redirectUrl);
        res.sendRedirect(redirectUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
