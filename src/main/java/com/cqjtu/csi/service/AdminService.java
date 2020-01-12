package com.cqjtu.csi.service;

import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.security.token.AuthToken;

public interface AdminService {

    AuthToken login(LoginParam loginParam);

}
