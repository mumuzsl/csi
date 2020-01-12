package com.cqjtu.csi.service;

import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface UserService {

    /**
     * Checks the password is match the user password.
     *
     * @param user          user info must not be null
     * @param plainPassword plain password
     * @return true if the given password is match the user password; false otherwise
     */
    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);

    void register(UserParam userParam);

    void login(LoginParam loginParam);

    Optional<User> getByLoginName(String username);

    User getByLoginNameOfNonNull(String username);
}
