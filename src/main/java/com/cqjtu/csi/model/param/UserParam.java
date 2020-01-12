package com.cqjtu.csi.model.param;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class UserParam implements InputConverter<User> {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名的字符长度不能超过 {max}")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 100, message = "密码的字符长度必须在 {min} - {max} 之间")
    private String password;



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
