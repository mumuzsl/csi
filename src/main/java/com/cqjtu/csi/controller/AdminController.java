package com.cqjtu.csi.controller;


import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.AdminService;
import com.cqjtu.csi.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author mumu
 * @date 2020/1/11
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "login")
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return adminService.login(loginParam);
    }

    @PostMapping(value = "logout")
    public Object logout() {
        return adminService.logout();
    }

    @PostMapping(value = "add")
    public Object add(@RequestBody @Valid UserParam userParam) {
        return null;
    }
//    @PostMapping(value = "register")
//    public Integer register(@RequestBody @Valid UserService )
}
