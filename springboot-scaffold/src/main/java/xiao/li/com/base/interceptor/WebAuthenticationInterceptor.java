package xiao.li.com.base.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xiao.li.com.base.authorization.JwtLocalManager;
import xiao.li.com.base.constant.CpsRsaConstants;
import xiao.li.com.base.user.CurrentUser;
import xiao.li.com.base.user.CurrentUserThreadLocalUtils;
import xiao.li.com.base.user.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * token验证
 */
@Slf4j
public class WebAuthenticationInterceptor extends HandlerInterceptorAdapter {


    private static final String AUTHENTICATION_HEAD_FIELD = "X-Auth-Token";
    public static final String MESSAGE_FIELD="errorMsg";
    public static final String AUTH_FAILURE_MESSAGE = "TOKEN校验失败,请重新登录";

    @Autowired
    private JwtLocalManager jwtLocalManager;

    private final String WMS_SUBSYSTEM_CODE = "00000001";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String jwt = request.getHeader(AUTHENTICATION_HEAD_FIELD);
        if (StringUtils.isBlank(jwt)) {
            log.error("headers没有[{}]字段或者值为空", AUTHENTICATION_HEAD_FIELD);
            responseForbidden(response);
            return false;
        }

        JwtClaims claims = JwtUtil.verifyJwt(CpsRsaConstants.CPS_PUB_KEY, jwt);
        if (claims == null) {
            log.error("jwt验签失败");
            responseForbidden(response);
            return false;
        }

        Map<String, String> subsystem = (Map<String, String>) claims.getClaimValue("subsystem");
        Map<String, String> auth = (Map<String, String>) claims.getClaimValue("auth");

        if(CollectionUtil.isEmpty(subsystem)){
            log.error("subsystem=[{}]为空", subsystem);
            responseForbidden(response);
            return false;
        }


        if(!subsystem.containsKey(WMS_SUBSYSTEM_CODE)){
            log.error("subsystem=[{}]不具有[{}]子系统权限", subsystem, WMS_SUBSYSTEM_CODE);
            responseForbidden(response);
            return false;
        }


        String empNo = (String) claims.getClaimValue("empNo");
        Long  authVersion = (Long)claims.getClaimValue("authVersion");
        boolean isJwtValid = jwtLocalManager.isJwtAuthVersionValid(empNo, authVersion);

        if(!isJwtValid){
            responseForbidden(response);
            return false;
        }


        Long userId = (Long) claims.getClaimValue("userId");
        String loginName = (String) claims.getClaimValue("loginName");
        String name  = (String) claims.getClaimValue("name");

        CurrentUser currentUser = new CurrentUser();
        currentUser.setUserId(userId);
        currentUser.setAuth(auth);
        currentUser.setName(name);
        currentUser.setLoginName(loginName);
        currentUser.setEmpNo(empNo);
        CurrentUserThreadLocalUtils.set(currentUser);
      

        return true;
    }

    void responseForbidden(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        JSONObject ret = new JSONObject();
        ret.put(MESSAGE_FIELD, AUTH_FAILURE_MESSAGE);
        PrintWriter printWriter = response.getWriter();
        try {
            printWriter.write(ret.toJSONString());
            printWriter.flush();
        }finally {
            printWriter.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentUserThreadLocalUtils.remove();
    }
}
