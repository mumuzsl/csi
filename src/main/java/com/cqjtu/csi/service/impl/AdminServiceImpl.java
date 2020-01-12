package com.cqjtu.csi.service.impl;

import cn.hutool.core.lang.Validator;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.AdminService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service
public class AdminServiceImpl implements AdminService {

    /**
     * Expired seconds.
     */
    private final static int ACCESS_TOKEN_EXPIRED_SECONDS = 60;

    private final UserService userService;

    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        Assert.notNull(loginParam, "Login param must not be null");

        String username = loginParam.getLoginName();

        String mismatchTip = "用户名或者密码不正确";

        final User user;

        try {
            user = userService.getByLoginNameOfNonNull(username);
        } catch (NotFoundException e) {
            throw new BadRequestException(mismatchTip);
        }

        if (!userService.passwordMatch(user, loginParam.getPassword())) {
            // If the password is mismatch
            throw new BadRequestException(mismatchTip);
        }

        // Generate new token
        return buildAuthToken(user);
    }

    /**
     * Builds authentication token.
     *
     * @param user user info must not be null
     * @return authentication token
     */
    @NonNull
    private AuthToken buildAuthToken(@NonNull User user) {
        Assert.notNull(user, "User must not be null");

        // Generate new token
        AuthToken token = new AuthToken();

        token.setAccessToken(BaseUtils.randomUUIDWithoutDash());
        token.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);
        token.setRefreshToken(BaseUtils.randomUUIDWithoutDash());

        return token;
    }
}
