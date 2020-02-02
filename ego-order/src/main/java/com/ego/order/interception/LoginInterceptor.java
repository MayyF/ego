package com.ego.order.interception;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:S
 * @Date:20/2/1
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final String COOKIEKEY="TT_TOKEN";
    private String reqUrl="http://localhost:8084/user/token/%s";
    private String reqDir="http://localhost:8084/user/showLogin?interurl=%s?num=%s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token= CookieUtils.getCookieValue(request,COOKIEKEY);

        if(token!=null&&!token.equals("")){
            String url=String.format(reqUrl,token);
            String json= HttpClientUtil.doPost(url);
            EgoResult er= JsonUtils.jsonToPojo(json,EgoResult.class);
            if(er.getStatus()==200){
                return  true;
            }
        }
        String num=request.getParameter("num");
        String redirectUrl=String.format(reqDir,request.getRequestURL(),num);
        response.sendRedirect(redirectUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
