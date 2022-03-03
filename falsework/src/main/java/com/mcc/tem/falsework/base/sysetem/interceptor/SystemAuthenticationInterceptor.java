package com.mcc.tem.falsework.base.sysetem.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mcc.tem.falsework.base.sysetem.ThreadLocal.CurrentUser;
import com.mcc.tem.falsework.base.sysetem.ThreadLocal.CurrentUserThreadLocalUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/****
* 自定义拦截器，讲token中的用户信息保存到CurrentUserThreadLocal当中 <br>
*
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2022/3/3 16:13 <br>
**/
@Configuration
@EnableAsync
public class SystemAuthenticationInterceptor extends HandlerInterceptorAdapter {


    private static final String AUTHENTICATION_HEAD_FIELD = "X-Auth-Token";
    public static final String MESSAGE_FIELD="errorMsg";
    public static final String AUTH_FAILURE_MESSAGE = "TOKEN校验失败,请重新登录";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        String tokenStr = request.getHeader(AUTHENTICATION_HEAD_FIELD);
//        if (StringUtils.isNotBlank(tokenStr)) {
//            CurrentUser currentUser = JwtUtil.verifyAccessIdToken(tokenStr);
//            if (currentUser != null) {
//                CurrentUserThreadLocalUtils.set(currentUser);
//                return true;
//            }
//        }
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.setContentType("application/json");
//        JSONObject ret = new JSONObject();
//        ret.put(MESSAGE_FIELD, AUTH_FAILURE_MESSAGE);
//        PrintWriter printWriter = response.getWriter();
//        try {
//            printWriter.write(ret.toJSONString());
//            printWriter.flush();
//        }finally {
//            printWriter.close();
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        CurrentUserThreadLocalUtils.remove();
    }
}
