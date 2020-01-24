package com.cqjtu.csi.controller.api;

import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.UserParam;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mumu
 * @date 2020/1/11
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "login")
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @PostMapping(value = "logout")
    public Object logout() {
        return userService.logout();
    }

    @GetMapping
    public Page search(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "status", required = false, defaultValue = "0") String status, @PageableDefault Pageable pageable) {
        return null == keyword ? userService.pageBy(pageable) : userService.search(keyword, status, pageable);
    }
}