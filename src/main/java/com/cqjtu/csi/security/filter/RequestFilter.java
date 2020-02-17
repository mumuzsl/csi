package com.cqjtu.csi.security.filter;

import com.cqjtu.csi.exception.AuthenticationException;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static cn.hutool.http.HttpStatus.HTTP_FORBIDDEN;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class RequestFilter extends AbstractFilter {
    protected final TokenService tokenService;

    public RequestFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    String getToken(HttpServletRequest request) {
        log.info("request path: {}", request.getServletPath());

        request.getParameterMap().forEach((key, value) -> {
            log.info("{} : {}", key, String.join(",", value));
        });

//        log.info("token : {}", request.getParameter("token"));

//        return request.getHeader("token");
        return request.getParameter("token");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (StringUtils.isBlank(token)) {
            failureHandler.doHandle(request, response, new AuthenticationException("没有token"));
            return;
        }

        Optional<Token> tokenOptional = tokenService.getOne(token);

        boolean flag = !tokenOptional.isPresent() || tokenService.isExpired(tokenOptional.get());

        if (flag) {
            failureHandler.doHandle(request, response, new AuthenticationException("token无效"));
            return;
        }

        log.info("request is ok");

        filterChain.doFilter(request, response);
    }
}
