package com.app.ctrl.common.jwt;

public class UserContext {
    private static ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();

    public static void setUser(LoginUser loginUser) {
        userHolder.set(loginUser);
    }

    public static LoginUser getUser() {
        return userHolder.get();
    }
}
