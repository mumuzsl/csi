package com.cqjtu.csi.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.core.FaceClient;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.Notice;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.NoticeParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.repository.UserRepository;
import com.cqjtu.csi.security.context.AdminContext;
import com.cqjtu.csi.security.support.UserDetail;
import com.cqjtu.csi.security.support.UserStatus;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.BeanUtils;
import com.cqjtu.csi.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/11
 */
@Service
public class UserServiceImpl extends AbstractCrudService<User, Integer> implements UserService {

    /**
     * Expired seconds.
     */
    private final static int ACCESS_TOKEN_EXPIRED_SECONDS = 60;
    private final static String MISMACTH_TIP = "用户名或者密码不正确";
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CacheStore<String, String> cacheStore;

    protected UserServiceImpl(UserRepository userRepository, TokenService tokenService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.cacheStore = new InMemoryCacheStore();
        this.tokenService = tokenService;
    }

    @Override
    public BaseResponse faceMatch(String base64) {
        JSONObject object = FaceClient.search(base64);

        if (FaceClient.verify(object)) {
            Integer useId = FaceClient.getUseId(object);
            User user = userRepository.getOne(useId);
            return BaseResponse.ok(CsiConst.LOGIN_SUCCESS, buildAuthToken(user));
        } else {
            return BaseResponse.to400(CsiConst.LOGIN_FAILED);
        }
    }

    @Override
    public BaseResponse addFace(Integer id, String base64) {
        String faceToken = FaceClient.addFace(id, base64);
        return BaseResponse.ok(CsiConst.FACE_ADD_SUCCESS);
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        Assert.notNull(loginParam, "Login param must not be null");

        String username = loginParam.getLoginName();

        final User user;

        try {
            user = getByLoginNameOfNonNull(username);
        } catch (NotFoundException e) {
            throw new BadRequestException(MISMACTH_TIP);
        }

        if (!passwordMatch(user, loginParam.getPassword())) {
            // If the password is mismatch
            throw new BadRequestException(MISMACTH_TIP);
        }

        AdminContext.setAdminContext(new UserDetail(user, UserStatus.LOGIN));

        Token token = tokenService.createToken(user.getId());

        // Generate new AuthToken
        return new AuthToken(token);
    }

    /**
     * @return
     */
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

    @Override
    public Object logout(Integer id, AuthToken authToken) {
        Integer userId = userRepository.findById(id)
                .map(User::getId)
                .orElseThrow(() -> new NotFoundException("没有找到该用户"));

        return null;
    }

    @Override
    public void logout(AuthToken authToken) {
        tokenService.clearToken(authToken.getAccessToken());
    }

    @Override
    public Optional<User> getByLoginName(String loginName) {
        return userRepository.findByLoginName(loginName);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByLoginNameOfNonNull(String username) {
        return getByLoginName(username).orElseThrow(() -> new NotFoundException("The username dose not exist").setErrorData(username));
    }

    @Override
    public void register(UserParam userParam) {

        User user = userParam.convertTo();

        userRepository.save(user);
    }

    @Override
    public void registerAdmin() {
        User user = new User();
        user.setLoginName("root");
        user.setPassword("1234");
        user.setUsername("zhao");
        user.setStatus(1);
        userRepository.save(user);
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        Assert.notNull(user, "User must not be null");

        return !StringUtils.isBlank(plainPassword) && BCrypt.checkpw(plainPassword, user.getPassword());
    }

    @Override
    public Page<User> search(String keyword, String status, Pageable pageable) {
        return StringUtils.isBlank(status) ?
                userRepository.findByUsernameContaining(keyword, pageable) :
                userRepository.findByUsernameContainingAndStatusEquals(
                        keyword,
                        BaseUtils.userStatus(status),
                        pageable
                );
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

    @Override
    public <T> T check(InputConverter<T> noticeParam) {
        Optional<User> userOptional = getByUsername(String.valueOf(BeanUtils.getFieldValue(noticeParam, "username")));
        if (!userOptional.isPresent()) {
            throw new BadRequestException("用户不存在");
        }
        return noticeParam.convertTo();
    }
}
