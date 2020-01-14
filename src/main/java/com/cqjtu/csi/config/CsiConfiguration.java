package com.cqjtu.csi.config;

import com.cqjtu.csi.security.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mumu
 * @date 2020/1/13
 */
@Configuration
public class CsiConfiguration {

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        AdminFilter adminFilter = new AdminFilter();

        adminFilter.addExcludeUrlPatterns(
                "/admin/login",
                "/admin/logout"
        );

        FilterRegistrationBean<AdminFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(adminFilter);
        filterRegistrationBean.addUrlPatterns("/admin/*");
        filterRegistrationBean.setOrder(0);

        return filterRegistrationBean;
    }

}
