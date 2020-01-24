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

/**
 * @author mumu
 * @date 2020/1/11
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;

    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

}
