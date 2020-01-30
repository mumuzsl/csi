package com.cqjtu.csi.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author mumu
 * @date 2020/1/24
 */
@Api("人脸管理接口")
@RestController
@RequestMapping(value = "/api/face")
@CrossOrigin
public class FaceController {

    private UserService userService;

    public FaceController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "proxy")
    public void proxy(@RequestBody String data) {
        System.out.println(data);
    }

    @PostMapping("login")
    public BaseResponse face(@RequestBody String base64) {
        return userService.faceMatch(JSONObject.parseObject(base64).getString("data"));
    }

    @PostMapping("register/{id:\\d+}")
    public BaseResponse faceRegister(@PathVariable("id") Integer id, @RequestBody String base64) {
        return userService.addFace(id, JSONObject.parseObject(base64).getString("data"));
    }
}
