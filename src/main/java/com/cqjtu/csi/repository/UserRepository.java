package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/11
 */
public interface UserRepository extends BaseRepository<User, Integer> {

    /**
     * Gets user by username.
     *
     * @param username username must not be blank
     * @return an optional user
     */
    @NonNull
    Optional<User> findByLoginName(@NonNull String username);

}
