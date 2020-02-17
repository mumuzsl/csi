package com.cqjtu.csi.security.filter;

import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mumu
 * @date 2020/2/15
 */
public class PageableFilter extends AbstractFilter {
    private static String VALUE = "0";
    public static final String[] PAGE_VALUE = new String[]{VALUE};

    public String[] modifyPageValue(String page) {
        try {
            VALUE = String.valueOf(safe(page));
        } catch (NumberFormatException e) {
            log.info("Invaild page paramter");
        }
        return PAGE_VALUE;
    }

    public Integer safe(String page) {
        int p = Integer.valueOf(page) - 1;
        return p >= 0 ? p : 0;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String page = request.getParameter("page");

        log.info("pageable filter path : {}", request.getServletPath());

        if (!StringUtils.isBlank(page)) {
            request.getParameterMap().put("page", modifyPageValue(page));
        }

        filterChain.doFilter(request, response);
    }
}
