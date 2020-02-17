package com.cqjtu.csi.security.filter;

import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.exception.AuthenticationException;
import com.cqjtu.csi.exception.BadRequestException;
import com.cqjtu.csi.model.entity.Token;
import com.cqjtu.csi.model.entity.User;
import com.cqjtu.csi.security.handle.FailureHandler;
import com.cqjtu.csi.security.token.AuthToken;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Stream;

/**
 * @author mumu
 * @date 2020/1/22
 */
public abstract class AbstractFilter extends OncePerRequestFilter {
    protected final Logger log = LoggerFactory.getLogger(RequestFilter.class);
    protected final AntPathMatcher antPathMatcher;
    protected final CacheStore<String, String> cacheStore;
    protected Set<String> excludeUrlPatterns = new HashSet<>(2);
    protected final FailureHandler failureHandler;

    public AbstractFilter() {
        this.antPathMatcher = new AntPathMatcher();
        this.cacheStore = new InMemoryCacheStore();
        this.failureHandler = new FailureHandler();
    }

    public void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");

        Collections.addAll(this.excludeUrlPatterns, excludeUrlPatterns);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Assert.notNull(request, "Http servlet request must not be null");

        return excludeUrlPatterns.stream().anyMatch(p -> antPathMatcher.match(p, request.getServletPath()));
    }

    protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        doFilter(request, response, filterChain);
    }
}
