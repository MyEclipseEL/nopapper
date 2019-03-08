package com.ladybird.hkd.interceptor;

import com.ladybird.hkd.util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        JWTUtils utils = new JWTUtils();
        String jwt = httpServletRequest.getHeader("Authorization");

        try {
            if (jwt == null) {
                System.out.println("用户未登录");
            } else {
                Claims c;
                c = utils.parseJWT(jwt);
                System.out.println("用户id" + c.get("id"));
                return true;
            }
            System.out.println("token解析错误");
            httpServletResponse.getWriter().write("未登录，请重新操作");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
