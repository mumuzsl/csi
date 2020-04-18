package com.cqjtu.csi.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.core.FaceClient;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.core.role.Role;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.DataException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.PasswordParam;
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
    private static final int ACCESS_TOKEN_EXPIRED_SECONDS = 60;
    private static final String MISMACTH_TIP = "用户名或者密码不正确";
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
            AuthToken authToken = new AuthToken(tokenService.createToken(FaceClient.getUserId(object)));
            return BaseResponse.ok(CsiConst.LOGIN_SUCCESS, authToken);
        } else {
            return BaseResponse.to400(CsiConst.LOGIN_FAILED);
        }
    }

    @Override
    public BaseResponse addFace(Integer id, String base64) {
        FaceClient.addFace(id, base64);
        return BaseResponse.ok(CsiConst.FACE_ADD_SUCCESS);
    }

    @Override
    public BaseResponse addFaceByToken(String token, String base64) {
        Optional<Integer> userIdOpt = tokenService.getOne(token).map(Token::getUserId);
        return userIdOpt.isPresent() ? addFace(userIdOpt.get(), base64) : BaseResponse.to400("无法找到token对应的用户");
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
    public User update(User user) {
        user.setPassword(null);
        return super.update(user);
    }

    @Override
    public void logout(AuthToken authToken) {
        logout(authToken.getAccessToken());
    }

    @Override
    public void logout(String token) {
        tokenService.clearToken(token);
    }

    @Override
    public Optional<User> getByToken(String token) {
        return tokenService.getOne(token)
                .map(Token::getUserId)
                .map(this::getOneById)
                .orElse(null);
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
    public User insert(User user) {
        if (getByLoginName(user.getLoginName()).isPresent()) {
            throw new BadRequestException("登录名已存在");
        }
        String password = user.getPassword();
        if (StringUtils.isBlank(password) || password.length() < 4 || password.length() > 20) {
            throw new BadRequestException("密码不能为空");
        }
        return super.insert(user);
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
                        Role.userStatus(status),
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

    @Override
    public String addUsername(Integer id) {
        return Optional.ofNullable(id)
                .map(this::getOne)
                .map(User::getUsername)
                .orElse("用户不存在");
    }

    @Override
    public void updatePassword(String token, PasswordParam pw) {
        Integer userId = tokenService.getByToken(token).getUserId();
        User one = getOne(userId);
        if (!passwordMatch(getOne(userId), pw.getOldPassword())) {
            throw new BadRequestException("旧密码不正确");
        }
        User user = new User();
        user.setPassword(pw.getNewPassword());
        updateById(userId, user);
    }
}
