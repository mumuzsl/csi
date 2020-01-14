package com.cqjtu.csi.model.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @author mumu
 * @date 2020/1/11
 */
public class LoginParam {

    @NotBlank(message = "登录名不能为空")
    @Size(max = 255, message = "登录名的字符长度不能超过 {max}")
    private String loginName;

    @NotBlank(message = "登陆密码不能为空")
    @Size(max = 100, message = "密码的字符长度不能超过 {max}")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginParam{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
