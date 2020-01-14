package com.cqjtu.csi.service;

import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.security.token.AuthToken;

/**
 * @author mumu
 * @date 2020/1/11
 * @see com.cqjtu.csi.service.impl.AdminServiceImpl
 */
public interface AdminService {

    AuthToken login(LoginParam loginParam);

    Object logout();

}
