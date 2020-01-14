package com.cqjtu.csi.security.context;

import com.cqjtu.csi.security.support.ContextWrapper;
import com.cqjtu.csi.security.support.UserDetail;

/**
 * @author mumu
 * @date 2020/1/14
 */
public class AdminContext {

    private final static ContextWrapper<UserDetail> CONTEXT = new ContextWrapper<>();

    public static void setAdminContext(UserDetail context) {
        CONTEXT.set(context);
    }

    public static UserDetail getAdminContext() {
        return CONTEXT.get();
    }
}
