package xiao.li.com.base.config;

import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * http状态码在[400-500)之间的，返回增加格式化信息{status:status, errorMsg:errorMsg, path:path} <br>
 *
 * @author hao.wang <br>
 * @version 1.0 <br>
 * @createDate 2020/5/29 11:15 <br>
 *
 */
public class BaseErrorAttributes implements ErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>(10);
        Integer status = (Integer) getAttribute(webRequest, "javax.servlet.error.status_code");
        errorAttributes.put("status", status);
        String errorMsg = "系统错误，未处理异常";
        if (status < HttpStatus.SC_INTERNAL_SERVER_ERROR && status >= HttpStatus.SC_BAD_REQUEST) {
            errorMsg = "请求异常";
        }
        errorAttributes.put("errorMsg", errorMsg);
        addPath(errorAttributes, webRequest);
        return errorAttributes;
    }

    private void addPath(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        String path = (String) getAttribute(requestAttributes, "javax.servlet.error.request_uri");
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

    private Object getAttribute(RequestAttributes requestAttributes, String name) {
        return requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return null;
    }
}
