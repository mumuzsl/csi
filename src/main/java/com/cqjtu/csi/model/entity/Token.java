package com.cqjtu.csi.model.entity;

import com.cqjtu.csi.security.token.AuthToken;

import javax.persistence.*;

/**
 * @author mumu
 * @date 2020/1/28
 */
@Entity
@Table(name = "token",
        indexes = {@Index(columnList = "token")})
public class Token extends BaseEntity {

    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expired_in")
    private Integer expiredIn;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(Integer expiredIn) {
        this.expiredIn = expiredIn;
    }

    @Override
    protected void prePersist() {
        super.prePersist();

        if (expiredIn == null) {
            expiredIn = 60;
        }
    }

    public long getExpiredDate() {
        return getCreateTime().getTime() + expiredIn.longValue() * 1000;
    }

    @Override
    public String toString() {
        return "Token{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiredIn='" + expiredIn + '\'' +
                '}';
    }
}
