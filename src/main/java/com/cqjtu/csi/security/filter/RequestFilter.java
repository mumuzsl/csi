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

    public RequestFilter(TokenService tokenService) {
        super(tokenService);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (StringUtils.isBlank(token)) {
            response.sendError(HTTP_FORBIDDEN, "请求中不包含token");
        }

        Optional<Token> tokenOptional = tokenService.getOne(token);

        if (!tokenOptional.isPresent() || tokenService.isExpired(tokenOptional.get())) {
            failureHandler.doHandle(request, response, new AuthenticationException("token无效"));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
