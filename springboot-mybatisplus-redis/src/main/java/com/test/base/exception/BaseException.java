package com.test.base.exception;


/*
* Description <br> 
*
* @param null <br>  
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2021/12/10 15:40 <br>
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
