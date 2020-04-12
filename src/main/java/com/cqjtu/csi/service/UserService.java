package com.cqjtu.csi.service;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/11
 * @see com.cqjtu.csi.service.impl.UserServiceImpl
 */
public interface UserService extends CrudService<User, Integer> {

    /**
     * Checks the password is match the user password.
     *
     * @param user          user info must not be null
     * @param plainPassword plain password
     * @return true if the given password is match the user password; false otherwise
     */
    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);

    void register(UserParam userParam);

    void registerAdmin();

    AuthToken login(LoginParam loginParam);

    @Deprecated
    Object logout();

    Object logout(Integer id, AuthToken authToken);

    void logout(AuthToken authToken);

    void logout(String token);

    Optional<User> getByToken(String token);

    Optional<User> getByLoginName(String username);

    Optional<User> getByUsername(String username);

    User getByLoginNameOfNonNull(String username);

    BaseResponse faceMatch(String base64);

    BaseResponse addFace(Integer id, String base64);

    BaseResponse addFaceByToken(String token, String base64);

    Page<User> search(String keyword, String status, Pageable pageable);

    <T> T check(InputConverter<T> param);

    String addUsername(Integer id);
}
