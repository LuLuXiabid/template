package xiao.li.com.base.exception;


/**
 * BaseException <br>
 *
 * @author hao.wang <br>
 * @version 1.0 <br>
 * @createDate 2020/5/29 11:17 <br>
 * @see com.changhong.mcc.base.exception <br>
 */
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
