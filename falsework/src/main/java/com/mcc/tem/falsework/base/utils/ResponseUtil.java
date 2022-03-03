package com.mcc.tem.falsework.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
* Description <br>
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2022/3/3 19:10 <br>
**/
@Slf4j
public class ResponseUtil {

    private static final String ERROR_STATUS = "status";
    private static final String ERROR_MESSAGE_KEY = "errorMsg";

    private ResponseUtil() {
    }

    public static ResponseEntity<JSONObject> badRequest(Object msg) {
        log.error(msg.toString());
        JSONObject json = new JSONObject();
        json.put(ERROR_STATUS, HttpStatus.BAD_REQUEST.value());
        json.put(ERROR_MESSAGE_KEY, msg);
        return ResponseEntity.badRequest().body(json);
    }

    public static ResponseEntity<JSONObject> notFound(Object msg) {
        log.error(msg.toString());
        JSONObject json = new JSONObject();
        json.put(ERROR_STATUS, HttpStatus.NOT_FOUND.value());
        json.put(ERROR_MESSAGE_KEY, msg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
    }

    public static ResponseEntity<JSONObject> unauthorized(Object msg) {
        log.error(msg.toString());
        JSONObject json = new JSONObject();
        json.put(ERROR_STATUS, HttpStatus.UNAUTHORIZED.value());
        json.put(ERROR_MESSAGE_KEY, msg);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json);
    }

    public static ResponseEntity<JSONObject> serverError() {
        JSONObject json = new JSONObject();
        json.put(ERROR_STATUS, HttpStatus.SERVICE_UNAVAILABLE.value());
        json.put(ERROR_MESSAGE_KEY, "系统错误，请联系管理员");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
    }

    public static ResponseEntity<JSONObject> serverError(String msg) {
        JSONObject json = new JSONObject();
        json.put(ERROR_STATUS, HttpStatus.SERVICE_UNAVAILABLE.value());
        json.put(ERROR_MESSAGE_KEY, msg);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
    }

    public static ResponseEntity<JSONObject> ok() {
        return ResponseEntity.ok(new JSONObject());
    }


    public static <T>ResponseEntity<T> ok(T t) {
        return ResponseEntity.ok(t);
    }

    public static ResponseEntity<JSONObject> badReturn(Object msg) {
        JSONObject json = JSONObject.parseObject(JSON.toJSONString(msg));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

    public static ResponseEntity<JSONObject> ok(JSONObject object) {
        return ResponseEntity.ok(object);
    }
}
