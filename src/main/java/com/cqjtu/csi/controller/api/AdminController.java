package com.cqjtu.csi.controller.api;


import com.cqjtu.csi.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author mumu
 * @date 2020/1/11
 */
@Deprecated
@Api(tags = {"该接口已废弃"})
@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


}
