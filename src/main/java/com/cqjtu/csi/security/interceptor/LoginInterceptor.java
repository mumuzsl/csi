package com.cqjtu.csi.security.interceptor;

import com.cqjtu.csi.cache.CacheStore;
import com.cqjtu.csi.cache.InMemoryCacheStore;
import com.cqjtu.csi.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/21
 */
public class LoginInterceptor implements HandlerInterceptor {
    private final CacheStore<String, String> cacheStore;

    public LoginInterceptor() {
        this.cacheStore = new InMemoryCacheStore();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("interceptor");
        String path = request.getServletPath();
        System.out.println(path);
        String token = request.getHeader("token");
        System.out.println(token);

        Optional<String> optionalUserId = cacheStore.get(token);

        if (!optionalUserId.isPresent()) {
            response.sendError(400, "未登录");
            System.out.println("停止拦截");
            return false;
        }

        System.out.println("继续拦截");
        return true;
    }
}
