package com.mcc.tem.falsework.base.sysetem.exception;


/**
* 自定义异常 <br>
*
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2022/3/3 16:58 <br>
**/
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8551490277923253052L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message, new Throwable(message));
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
