package com.cqjtu.csi.security.filter;

import com.cqjtu.csi.core.role.Role;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mumu
 * @date 2020/1/12
 */
public class RoleFilter extends RequestFilter {
    private final UserService userService;

    public RoleFilter(UserService userService, TokenService tokenService) {
        super(tokenService);
        this.userService = userService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        Integer userId = tokenService.getByToken(token).getUserId();
        User user = userService.getOne(userId);

        if (Role.ADMIN.compare(user.getStatus())) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }

    }

}
