package com.cqjtu.csi.core.role;

import java.util.*;

/**
 * @author mumu
 * @date 2020/1/21
 */
public enum Role {
    /**
     * 管理员
     */
    ADMIN("admin", 1),
    /**
     * 普通用户
     */
    NORMAL("normal", 2);

    private static final HashMap<String, Role> ROLES = new HashMap<>(2);

    static {
        ROLES.put("0", ADMIN);
        ROLES.put("1", NORMAL);
    }

    private String roleName;
    private int roleStatus;

    Role(String roleName, int roleStatus) {
        this.roleName = roleName;
        this.roleStatus = roleStatus;
    }

    public static Integer userStatus(String status) {
        return Optional.ofNullable(ROLES.get(status))
                .map(Role::getRoleStatus)
                .orElse(0);
    }

    public boolean compare(Integer status) {
        return roleStatus == status;
    }

    public boolean compare(String status) {
        return String.valueOf(this.roleStatus).equals(status);
    }

    public String getRoleName() {
        return roleName;
    }

    void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleStatus() {
        return roleStatus;
    }

    void setRoleStatus(int roleStatus) {
        this.roleStatus = roleStatus;
    }
}
