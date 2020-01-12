package com.cqjtu.csi.controller;

import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "login")
    public void login(@RequestBody @Valid LoginParam loginParam) {
        userService.login(loginParam);
    }

    @PostMapping(value = "register")
    public void register(@RequestBody @Valid UserParam userParam) {
        userService.register(userParam);
    }

}