package com.cqjtu.csi.service.impl;

import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.security.context.AdminContext;
import com.cqjtu.csi.security.support.UserDetail;
import com.cqjtu.csi.security.support.UserStatus;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.AdminService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.NonUniqueResultException;

@Service
public class AdminServiceImpl implements AdminService {

    /**
     * Expired seconds.
     */
    private final static int ACCESS_TOKEN_EXPIRED_SECONDS = 60;
    private final static String MISMACTH_TIP = "用户名或者密码不正确";

    private final UserService userService;
    private final CacheStore<String, String> cacheStore;

    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
        this.cacheStore = new InMemoryCacheStore();
        creatAdminInDB();
    }

    public void creatAdminInDB() {
        try {
            System.out.println("check admin...");
            userService.getByLoginNameOfNonNull("root");
            System.out.println("admin exists.");
        } catch (NonUniqueResultException e) {
            System.out.println("admin not only, place delete admin info in db.");
        } catch (NotFoundException e) {
            System.out.println("admin not exists.");
            System.out.println("create admin...");
            userService.registerAdmin();
            System.out.println("create admin finish.");
        }
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        Assert.notNull(loginParam, "Login param must not be null");

        String username = loginParam.getLoginName();

        final User user;

        try {
            user = userService.getByLoginNameOfNonNull(username);
        } catch (NotFoundException e) {
            throw new BadRequestException(MISMACTH_TIP);
        }

        if (!userService.passwordMatch(user, loginParam.getPassword())) {
            // If the password is mismatch
            throw new BadRequestException(MISMACTH_TIP);
        }

        AdminContext.setAdminContext(new UserDetail(user, UserStatus.LOGIN));

        // Generate new token
        return buildAuthToken(user);
    }

    @Override
    public Object logout() {
        UserDetail userDetail = AdminContext.getAdminContext();

        Assert.notNull(userDetail, "you no login!");

        if (userDetail.getUserStatus() != UserStatus.LOGIN) {
            throw new BadRequestException("you no login!");
        }

        cacheStore.delete(userDetail.toKey());

        return "logout seccuud";
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

        cacheStore.put(user.getId().toString(), token.getAccessToken());
        cacheStore.put(token.getAccessToken(), user.getId().toString());

        return token;
    }
}
