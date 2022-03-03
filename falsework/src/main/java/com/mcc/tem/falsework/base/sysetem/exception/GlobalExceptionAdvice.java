package com.mcc.tem.falsework.base.sysetem.exception;

import com.alibaba.fastjson.JSONObject;
import com.mcc.tem.falsework.base.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/***
* 自定义异常 <br>
*
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2022/3/3 17:10 <br>
**/
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<JSONObject> handleBaseException(BaseException e) {
        log.error("业务异常！" + e.getMessage(), e);
        return ResponseUtil.badRequest(e.getMessage());
    }
}
