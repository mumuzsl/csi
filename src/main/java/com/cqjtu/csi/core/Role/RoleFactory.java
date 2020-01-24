package com.cqjtu.csi.core.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class RoleFactory {
    static Map<String, RoleOperation> roleOperationMap = new HashMap<>();

    static {
        roleOperationMap.put("ROLE_ADMIN", new Role("ROLE_ADMIN"));
        roleOperationMap.put("ROLE_NORMAL", new Role("ROLE_NORMAL"));
    }

    public static RoleOperation getOp(String roleName) {
        return roleOperationMap.get(roleName);
    }
}
