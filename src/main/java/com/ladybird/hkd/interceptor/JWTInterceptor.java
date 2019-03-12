package com.ladybird.hkd.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JWTUtils;
import com.ladybird.hkd.util.JsonUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenManager manager;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setCharacterEncoding("UTF-8");
        //如果不是映射到方法直接通过
        if (!(o instanceof HandlerMethod))
            return true;
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        //根据方法生命的注解走相应流程
        try {
            if (method.getAnnotation(CheckToken.class) != null) {
                //从header中得到token
                String tokenKey = getBearerToken(httpServletRequest);
                if (tokenKey == null || "".equals(tokenKey.trim())) {

                    return returnResponseMsg(httpServletResponse, HttpServletResponse.SC_FORBIDDEN, "无法正常获取token");
                }

                String checkMsg = lognRequired(tokenKey, httpServletRequest);
                if ("OK".equals(checkMsg)) {
                    return true;
                } else {
                    return returnResponseMsg(httpServletResponse, HttpServletResponse.SC_FORBIDDEN, checkMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 从header中获取token
     *@param request HttpRequest
     *@return String
     *Date: 2019/3/8
     */
    private String getBearerToken(HttpServletRequest request) throws Exception {
        //从header中得到token
        String authss = request.getHeader(ConstConfig.AUTHORIZATION);
        if (authss == null) {
            return null;
        }
        String[] aa = authss.split("\\ ");
        if (aa.length != 2) {
            return null;
        }
        String baseToken = aa[1];
        return baseToken;
    }

    /**
     * 包装返回前端的数据
     *@param response HttpServletResponse
     *@param code int
     *@param msg String
     *@return boolean
     *Date: 2019/3/9
     */
    private boolean returnResponseMsg(HttpServletResponse response, int code, String msg) {
        try {
            response.setStatus(code);
            ResultJson outJson = ResultJson.Forbidden(msg);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));

            writer.write(JsonUtil.objectToJson(outJson));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return false;
        }
    }

    private String lognRequired(String keyToken, HttpServletRequest request) {
        try {
            //验证token，只验证存在redis中
            String tokenValue = manager.checkToken(keyToken);
            if (tokenValue == null || "".equals(tokenValue.trim())) {
                return "权限过期请重新登陆";
            }
            //如果token验证成功，将token对应的用户uid存在request中，便于之后注入
            request.setAttribute(ConstConfig.CURRENT_OBJECT, tokenValue);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "出现异常，请联系管理员";
        }
    }


}
