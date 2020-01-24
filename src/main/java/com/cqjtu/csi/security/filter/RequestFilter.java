package com.cqjtu.csi.security.filter;

import com.cqjtu.csi.exception.AuthenticationException;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class RequestFilter extends AbstractFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (StringUtils.isBlank(token)) {
            throw new BadRequestException("请求中不包含token");
        }

        System.out.println(request.getContentType());
        filterChain.doFilter(request, response);
    }
}