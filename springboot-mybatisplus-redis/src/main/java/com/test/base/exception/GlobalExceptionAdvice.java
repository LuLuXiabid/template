package com.test.base.exception;

import com.alibaba.fastjson.JSONObject;
import com.test.base.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<JSONObject> handleBaseException(Exception e) {
        log.error("业务异常！" + e.getMessage(), e);
        return ResponseUtil.badRequest(e.getMessage());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<JSONObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("业务异常！" + ex.getMessage(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        return ResponseUtil.badRequest(msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("业务异常！" + ex.getMessage(), ex);
        return ResponseUtil.badRequest(ex.getMessage());
    } 
}
