package com.cqjtu.csi.repository;

import com.cqjtu.csi.exception.BaseException;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.repository.base.BaseRepository;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
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

    @Query(value = "select * from dept where user.username like concat('%',:name,'%') limit :first, :size", nativeQuery = true)
    List search(@Param("name") String name, @Param("first") Long first, @Param("size") Integer size);

    @Query(value = "select count(*) from dept where user.username like concat('%',:name,'%')", nativeQuery = true)
    Long countByName(@Param("name") String name);

    @NonNull
    Page<User> findByUsernameContainingAndStatusEquals(String loginName, Integer status, Pageable pageable);

    @NonNull
    Page<User> findByUsernameContaining(@NonNull String username, @NonNull Pageable pageable);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "UPDATE `user` SET `facepath` = :path WHERE `id` = :id", nativeQuery = true)
    void updateFacepath(@Param("id") Integer id, @Param("path") String path);
}
