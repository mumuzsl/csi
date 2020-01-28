package com.cqjtu.csi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.BaseEntity;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.repository.TokenRepository;
import com.cqjtu.csi.repository.base.BaseRepository;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.base.AbstractCrudService;
import com.cqjtu.csi.utils.BaseUtils;
import com.cqjtu.csi.utils.DateTimeUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/28
 */
@Service
public class TokenServiceImpl extends AbstractCrudService<Token, Integer> implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository repository) {
        super(repository);
        this.tokenRepository = repository;
    }

    @Override
    public Token createToken(@NonNull Integer userId) {
        Token token = new Token();

        token.setUserId(userId);
        token.setExpiredIn(CsiConst.ACCESS_TOKEN_EXPIRED_SECONDS);
        token.setToken(BaseUtils.randomUUIDWithoutDash());
        token.setRefreshToken(BaseUtils.randomUUIDWithoutDash());

        return insert(token);
    }

    @Override
    public void clearToken(String tokenStr) {
        Token token = getByToken(tokenStr);
        remove(token);
    }

    @Override
    public boolean isExpired(String token) {
        return isExpired(getByToken(token));
    }

    @Override
    public boolean isExpired(Token token) {
        return DateTimeUtils.after(token.getExpiredDate());
    }

    @Override
    public Token getByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException("没有登录记录"));
    }
}
