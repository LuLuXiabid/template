package xiao.li.com.base.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description
 * @Author lirong.deng
 * @Date 2020/3/16 10:36
 **/
@Data
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = 7367376816172542377L;

    /**
     * 用户id
     */
    private Long userId;


    private Map<String, String> subsystem;

    private String empNo;

    private String loginName;

    private String name;

    private Long authVersion;

    private Map<String, String> auth;
}
