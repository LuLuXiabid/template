package com.mcc.tem.falsework.base.sysetem.ThreadLocal;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/***
*  登陆时可以将当前用户信息保存到ThreadLocal中去，用的时候直接拿<br>
*
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2022/3/3 14:56 <br>
**/
@Data
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = 7367376816172542377L;

    /**
     * 用户id
     */
    private Long userId;

    private String loginName;

    private String name;

    private Long authVersion;

    private Map<String, String> auth;
}
