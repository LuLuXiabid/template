package com.mcc.tem.falsework.base.sysetem.ThreadLocal;

/**
 * 使用JWT token 用户信息线程隔离即可
 */
public class CurrentUserThreadLocalUtils {

    private static ThreadLocal<CurrentUser> currentUserThreadLocal = new ThreadLocal<>();

    public static CurrentUser get() {
        return currentUserThreadLocal.get();
    }

    public static void set(CurrentUser currentUser) {
        currentUserThreadLocal.set(currentUser);
    }

    public static void remove() {
        currentUserThreadLocal.remove();
    }
}
