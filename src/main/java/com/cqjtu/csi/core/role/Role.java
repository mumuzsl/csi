package com.cqjtu.csi.core.role;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class Role {
    public static final Role ADMIN = new Role("管理员", 1);
    public static final Role NORMAL = new Role("普通用户", 2);
    private static final HashMap<String, Role> ROLES = new HashMap<>(2);

    static {
        ROLES.put("0", ADMIN);
        ROLES.put("1", NORMAL);
    }

    private String roleName;
    private int roleStatus;

    public Role(String roleName, int roleStatus) {
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

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(int roleStatus) {
        this.roleStatus = roleStatus;
    }
}
