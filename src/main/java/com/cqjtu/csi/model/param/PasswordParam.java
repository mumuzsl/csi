package com.cqjtu.csi.model.param;

import javax.validation.constraints.NotBlank;

/**
 * @author mumu
 * @date 2020/4/18
 */
public class PasswordParam {

    @NotBlank(message = "不能为空")
    private String oldPassword;

    @NotBlank(message = "不能为空")
    private String newPassword;


    public boolean verify() {
        return false;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordParam{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
