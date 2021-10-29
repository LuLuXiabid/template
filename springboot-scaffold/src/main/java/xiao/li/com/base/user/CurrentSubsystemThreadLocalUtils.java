package xiao.li.com.base.user;

/**
 * @author shuai.zhang@changhong.com
 * @projectName jiaxipera-cps
 * @modifier
 * @Description 子系统线程上下文
 * @createTime 2021-02-08 11:26
 */
public class CurrentSubsystemThreadLocalUtils {

    private static ThreadLocal<CurrentSubsystem> currentSubsystemThreadLocal = new ThreadLocal<>();

    public static CurrentSubsystem get() {
        return currentSubsystemThreadLocal.get();
    }

    public static void set(CurrentSubsystem currentUser) {
        currentSubsystemThreadLocal.set(currentUser);
    }

    public static void remove() {
        currentSubsystemThreadLocal.remove();
    }

}
