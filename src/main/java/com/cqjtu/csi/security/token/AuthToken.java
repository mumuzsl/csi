package com.cqjtu.csi.security.token;

import com.cqjtu.csi.model.entity.Token;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class AuthToken {

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Expired in. (seconds)
     */
    private int expiredIn;

    /**
     * Refresh token.
     */
    private String refreshToken;

    public AuthToken() { }

    public AuthToken(Token token) {
        this.accessToken = token.getToken();
        this.expiredIn = token.getExpiredIn();
        this.refreshToken = token.getRefreshToken();
    }

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
