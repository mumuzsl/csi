package com.cqjtu.csi.core.Role;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class Role implements RoleOperation {
    public final static int ROLE_ADMIN = 1;
    public final static int ROLE_NORMAL = 2;

    private String roleName;
    private int roleStatus;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(int roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public String op() {
        return null;
    }
}
