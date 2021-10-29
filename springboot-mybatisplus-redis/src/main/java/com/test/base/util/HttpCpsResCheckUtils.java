package com.test.base.util;//package xiao.li.com.base.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import xiao.li.com.base.exception.BaseException;
//
///**
// * @author shuai.zhang@changhong.com
// * @projectName jiaxipera-wms
// * @modifier
// * @Description
// * @createTime 2021-03-19 15:34
// */
//@Slf4j
//public class HttpCpsResCheckUtils {
//
//    public static void checkResOk(ResponseEntity responseEntity){
//        if(responseEntity == null){
//            log.error("协同门户响应体为null");
//            throw new BaseException("协同门户响应体为null");
//        }
//        int statusValue = responseEntity.getStatusCodeValue();
//        if(HttpStatus.OK.value() == statusValue){
//            return;
//        }
//        String defaultErrorMsg = "协同门户返回状态码[" + statusValue + "]";
//        Object body = responseEntity.getBody();
//        if(body == null){
//            throw new BaseException(defaultErrorMsg);
//        }
//        String bodyStr = body.toString();
//        if(StringUtils.isBlank(bodyStr)){
//            throw new BaseException(defaultErrorMsg);
//        }
//
//        JSONObject json = null;
//        try{
//            json = JSON.parseObject(bodyStr);
//        }catch (Exception e){
//            log.error("解析协同平台返回json异常[{}]", json, e);
//            throw new BaseException(defaultErrorMsg);
//        }
//
//        String errorMsg = json.getString("errorMsg");
//        if(StringUtils.isBlank(errorMsg)){
//            throw new BaseException(defaultErrorMsg);
//        }
//
//        throw new BaseException(errorMsg);
//
//    }
//
//}
