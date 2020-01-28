package com.cqjtu.csi.security.filter;

import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.core.role.Role;
import com.cqjtu.csi.exception.AuthenticationException;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.exception.BaseException;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class RoleFilter extends AbstractFilter {
    private final UserService userService;

    public RoleFilter(UserService userService, TokenService tokenService) {
        super(tokenService);
        this.userService = userService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        Integer userId = tokenService.getByToken(token).getUserId();

        User user = userService.getById(userId);

        if (Role.ADMIN.compare(user.getStatus())) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
    }

}
