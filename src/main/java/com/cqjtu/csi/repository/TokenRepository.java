package com.cqjtu.csi.repository;

import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.repository.base.BaseRepository;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/28
 */
public interface TokenRepository extends BaseRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    boolean existsByToken(String token);
}
