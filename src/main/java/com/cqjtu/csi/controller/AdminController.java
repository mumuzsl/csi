package com.cqjtu.csi.controller;


import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.security.token.AuthToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(value = "/admin")
public class AdminController {

    private final AdminController adminController;

    public AdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @PostMapping(value = "login")
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return adminController.login(loginParam);
    }

}
