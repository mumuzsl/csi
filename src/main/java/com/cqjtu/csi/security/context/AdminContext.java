package com.cqjtu.csi.security.context;

import com.cqjtu.csi.security.support.ContextWrapper;
import com.cqjtu.csi.security.support.UserDetail;

public class AdminContext {

//    private final static ThreadLocal<UserDetail> THREAD_LOCAL = new ThreadLocal<>();

//    public static void setAdminContext(UserDetail context) {
//        THREAD_LOCAL.set(context);
//    }
//
//    public static UserDetail getAdminContext() {
//        return THREAD_LOCAL.get();
//    }

    private final static ContextWrapper<UserDetail> CONTEXT = new ContextWrapper<>();

    public static void setAdminContext(UserDetail context) {
        CONTEXT.set(context);
    }

    public static UserDetail getAdminContext() {
        return CONTEXT.get();
    }
}
