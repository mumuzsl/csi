package com.cqjtu.csi.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.security.token.AuthToken;
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

public class AdminFilter extends OncePerRequestFilter {

    protected final AntPathMatcher antPathMatcher;
    private final CacheStore<String, String> cacheStore;

    /**
     * Exclude url patterns.
     */
    private Set<String> excludeUrlPatterns = new HashSet<>(2);

    public AdminFilter() {
        this.antPathMatcher = new AntPathMatcher();
        this.cacheStore = new InMemoryCacheStore();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Assert.notNull(request, "Http servlet request must not be null");

        return excludeUrlPatterns.stream().anyMatch(p -> antPathMatcher.match(p, request.getServletPath()));
    }

    /**
     * Adds exclude url patterns.
     *
     * @param excludeUrlPatterns exclude urls
     */
    public void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");

        Collections.addAll(this.excludeUrlPatterns, excludeUrlPatterns);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Map map = request.getParameterMap();

        String tokenStr = request.getHeader("token");

        System.out.println(request.getServletPath());
        System.out.println(request.getHeader("token"));
//        System.out.println(request.getPart());

        AuthToken authToken = JSONObject.parseObject(tokenStr, AuthToken.class);

        map.forEach((key, value) -> {
            System.out.println(key + "：" + value);
        });

        Optional<String> s = cacheStore.get(authToken.getAccessToken());

        System.out.println(s.get());
    }
}
