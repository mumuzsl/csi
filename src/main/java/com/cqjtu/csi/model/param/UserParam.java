package com.cqjtu.csi.model.param;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.User;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author mumu
 * @date 2020/4/18
 */
public class UserParam implements InputConverter<User> {

    private int id;

    @NotBlank(message = "登录名不能为空")
    @Size(max = 20, message = "登录名的字符长度不能超过 {max}")
    private String loginName;

    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名的字符长度不能超过 {max}")
    private String username;

    private String password;

    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserParam{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
