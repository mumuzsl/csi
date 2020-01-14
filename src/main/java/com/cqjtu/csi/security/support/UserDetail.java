package com.cqjtu.csi.security.support;

import com.cqjtu.csi.model.entity.User;

/**
 * @author mumu
 * @date 2020/1/14
 */
public class UserDetail {

    private User user;

    private UserStatus userStatus;

    public UserDetail(User user, UserStatus userStatus) {
        this.user = user;
        this.userStatus = userStatus;
    }

    public String toKey() {
        return user.getId().toString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
