//package xiao.li.com.base.log;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
///**
// * @author shuai.zhang@changhong.com
// * @projectName jiaxipera-cps
// * @modifier
// * @Description 接口拦截打印
// * @createTime 2021-03-19 14:15
// */
//@Slf4j
//@Aspect
//@Component
//public class ControllerLogPrinter {
//
//
//    @Before(value = "controllerAspect()")
//    public void methodBefore(JoinPoint joinPoint){
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        String requestUri = request.getRequestURI();
//        log.info("<---请求uri:" + requestUri);
//        log.info("----请求类型:" + request.getMethod());
//        log.info(">---请求类方法参数:"+ Arrays.toString(joinPoint.getArgs()));
//    }
//
//
//    @Pointcut("execution(* xiao.li.com.*.controller.*.*(..))")
//    private void controllerAspect(){}
//
//}
