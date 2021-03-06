package com.cqjtu.csi.security.filter;

import cn.hutool.core.map.MapUtil;
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
        log.info("request-filter path: {}", request.getServletPath());

        // 输出request中的参数名和对应的值
        log.info("{{}}", MapUtil.join(request.getParameterMap(), ",", ":"));

//        return request.getParameter("token");
        return request.getHeader("token");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        log.info("doFilter");

        if (StringUtils.isBlank(token)) {
            failureHandler.doHandle(request, response, new AuthenticationException("没有token"));
            return;
        }

        log.info("doFilter2");

        if (!tokenService.verify(token)) {
            failureHandler.doHandle(request, response, new AuthenticationException("token无效"));
            return;
        }

        log.info("doFilter3");

        filterChain.doFilter(request, response);
    }
}
