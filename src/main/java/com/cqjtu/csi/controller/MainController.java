package com.cqjtu.csi.controller;

import com.alibaba.fastjson.JSON;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mumu
 * @date 2020/1/21
 */
@Controller
@RequestMapping
@CrossOrigin
public class MainController {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("login")
//    public String login() {
//        return "login/index";
//    }

    @PostMapping
    public void proxy(@RequestBody String body) {
        System.out.println(body);
    }

    @PostMapping(value = "register")
    @ResponseBody
    public BaseResponse register(@RequestBody @Valid UserParam userParam) {
        userService.register(userParam);
        return BaseResponse.ok(CsiConst.REGISTER_SUCCESS);
    }

    @PostMapping(value = "login")
    @ResponseBody
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        System.out.println("----------------------------------");
        return userService.login(loginParam);
    }

    @PostMapping(value = "logout")
    @ResponseBody
    public BaseResponse logout(@RequestBody String json) {
        AuthToken authToken = JSON.parseObject(json, AuthToken.class);
        userService.logout(authToken);
        return BaseResponse.ok("已退出");
    }
}
