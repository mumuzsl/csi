package com.cqjtu.csi.service;

import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.base.CrudService;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/28
 * @see com.cqjtu.csi.service.impl.TokenServiceImpl
 */
public interface TokenService extends CrudService<Token, Integer> {

    Token createToken(@NonNull Integer userId);

    void clearToken(@NonNull String token);

    Token getByToken(@NonNull String token);

    Optional<Token> getOne(@NonNull String token);

    boolean isExpired(String token);

    boolean isExpired(Token token);

}
