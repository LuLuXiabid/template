package xiao.li.com.base.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xiao.li.com.base.constant.CpsRsaConstants;
import xiao.li.com.base.user.CurrentSubsystem;
import xiao.li.com.base.user.CurrentSubsystemThreadLocalUtils;
import xiao.li.com.base.user.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author shuai.zhang@changhong.com
 * @projectName jiaxipera-cps
 * @modifier
 * @Description
 * @createTime 2021-02-02 10:59
 */
@Slf4j
public class SubsystemAuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final String AUTHENTICATION_HEAD_FIELD = "X-Auth-Token";
    public final String MESSAGE_FIELD="errorMsg";
    public final String AUTH_FAILURE_MESSAGE = "TOKEN校验失败,请重新登录";
    private final String SUBSYSYEM_CODE = "Subsystem-Code";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
//        String uri = request.getRequestURI();
//        if (StrUtil.contains(uri, "s_api/inventory") || StrUtil.contains(uri, "s_api/inbound")) {
//        	return true;
//        }

        String subsystemCode = request.getHeader(SUBSYSYEM_CODE);
        if(StringUtils.isBlank(subsystemCode)){
            log.error("headers没有[{}]字段或者值为空", SUBSYSYEM_CODE);
            responseForbidden(response);
            return false;
        }


        String tokenStr = request.getHeader(AUTHENTICATION_HEAD_FIELD);
        if (StringUtils.isBlank(tokenStr)) {
            log.error("headers没有[{}]字段或者值为空", AUTHENTICATION_HEAD_FIELD);
            responseForbidden(response);
            return false;
        }

        JwtClaims claims = JwtUtil.verifyJwt(CpsRsaConstants.CPS_PUB_KEY, tokenStr);
        if (claims == null) {
            log.error("jwt验签失败");
            responseForbidden(response);
            return false;
        }

        CurrentSubsystem currentSubsystem = new CurrentSubsystem();
        currentSubsystem.setSubsystemCode(subsystemCode);
        CurrentSubsystemThreadLocalUtils.set(currentSubsystem);

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
        CurrentSubsystemThreadLocalUtils.remove();
    }

}
