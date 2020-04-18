package com.cqjtu.csi.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IORuntimeException;
import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.exception.AuthenticationException;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.BaseException;
import com.cqjtu.csi.exception.NotFoundException;
import com.cqjtu.csi.model.entity.Document;
import com.cqjtu.csi.model.param.LoginParam;
import com.cqjtu.csi.model.param.PasswordParam;
import com.cqjtu.csi.model.support.BaseResponse;
import com.cqjtu.csi.security.handle.FailureHandler;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.CryptoService;
import com.cqjtu.csi.service.DocumentService;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import com.cqjtu.csi.utils.BaseUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;

/**
 * @author mumu
 * @date 2020/1/21
 */
@Controller
@RequestMapping
@CrossOrigin
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final UserService userService;
    private final DocumentService documentService;
    private final CryptoService cryptoService;
    private final TokenService tokenService;

    public MainController(UserService userService,
                          DocumentService documentService,
                          CryptoService cryptoService,
                          TokenService tokenService) {
        this.userService = userService;
        this.documentService = documentService;
        this.cryptoService = cryptoService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("index.html");
    }

    @GetMapping(value = "face/register")
    public void faceRegister() {

    }

    @PostMapping
    public void proxy(@RequestBody String body) {
        System.out.println(body);
    }

    //    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public AuthToken login(@RequestBody @Valid LoginParam loginParam,
                           @CookieValue(value = "rpw", required = false, defaultValue = "0") String rpw,
                           @CookieValue(value = "pw", required = false, defaultValue = "") String pw,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        log.info("rpw {}, pw {}", rpw, pw);
        if ("1".equals(rpw)) {
            if (!StringUtils.isBlank(pw)) {
                loginParam.setPassword(pw);
            } else {
                response.addCookie(new Cookie("pw", loginParam.getPassword()));
                response.addCookie(new Cookie("rpw", "1"));
            }
        }
        return userService.login(loginParam);
    }


    @GetMapping(value = "logout")
    public String logout(@RequestHeader("token") String token) {
//        userService.logout(token);
        return "logout";
    }

    @PostMapping(value = "download")
    @ResponseBody
    public String buildDownloadUrl(@RequestBody int id, @RequestHeader("token") String token) {
        String data = id + "&" + token;
        return cryptoService.enCrypt(data);
    }


    /**
     * 修改密码
     *
     * @param token
     * @param pw
     * @return
     */
    @PostMapping(value = "update_pw")
    @ResponseBody
    public BaseResponse updatePassword(@RequestHeader("token") String token, @RequestBody PasswordParam param) {
        userService.updatePassword(token, param);
        return BaseResponse.ok("更新密码成功");
    }

    @GetMapping(value = "download/{code}")
    public void download(@PathVariable("code") String code, HttpServletResponse response) throws IOException {
        try {
            int id = getId(code);
            if (id == -1) {
                FailureHandler.doHandle(response, new AuthenticationException("验证失败"));
                return;
            }
            Document one = documentService.getOne(id);
            File file = new File(CsiConst.DOCUMENT_DIR + one.getFilename());

            response.setContentType(FileTypeUtil.getType(file));
            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename=" +
                            BaseUtils.webStr(one.getTitle()) + "." +
                            FileTypeUtil.getType(file));
            response.setContentLengthLong(file.length());

            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));) {
                try (BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
                    byte[] buffer = new byte[0x1000];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }

        } catch (BadPaddingException e) {
            FailureHandler.doHandle(response, new BadRequestException("链接失效"));
        } catch (NotFoundException | IORuntimeException e) {
            FailureHandler.doHandle(response, new BadRequestException("文件不存在"));
        } catch (BaseException e) {
            FailureHandler.doHandle(response, e);
        } catch (Exception e) {
            log.error("download error", e);
            FailureHandler.doHandle(response, new BadRequestException("失败"));
        }
    }

    private int getId(String code) throws BadPaddingException {
        String s = cryptoService.deCrypt(code);
        log.info(s);
        final String[] split = s.split("&");
        String token = split[1];
        if (!tokenService.verify(token)) {
            return -1;
        }
        return Integer.parseInt(split[0]);
    }
}
