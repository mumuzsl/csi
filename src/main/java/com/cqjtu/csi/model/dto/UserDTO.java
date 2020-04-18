package com.cqjtu.csi.model.dto;


import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.cqjtu.csi.model.entity.User;

/**
 * @author mumu
 * @date 2020/4/12
 */
public class UserDTO implements OutputConverter<UserDTO, User> {

    private String loginName;

    private String username;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
