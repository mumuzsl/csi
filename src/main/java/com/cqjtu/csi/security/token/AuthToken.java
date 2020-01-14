package com.cqjtu.csi.security.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class AuthToken {

    /**
     * Access token.
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * Expired in. (seconds)
     */
    @JSONField(name = "expired_in")
    private int expiredIn;

    /**
     * Refresh token.
     */
    @JSONField(name = "refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(int expiredIn) {
        this.expiredIn = expiredIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
