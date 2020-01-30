package com.cqjtu.csi.config;

import com.cqjtu.csi.core.CsiConst;
import com.cqjtu.csi.security.filter.RequestFilter;
import com.cqjtu.csi.security.filter.RoleFilter;
import com.cqjtu.csi.security.handle.FailureHandler;
import com.cqjtu.csi.service.TokenService;
import com.cqjtu.csi.service.UserService;
import freemarker.core.TemplateClassResolver;
import freemarker.template.TemplateException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

/**
 * @author mumu
 * @date 2020/1/13
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cqjtu.csi.controller")
public class CsiConfigurer implements WebMvcConfigurer {

    private static final String FILE_PROTOCOL = "file:///";


    /**
     * 对api请求进行过滤，验证token，防止非登录情况下能够获取到数据。
     *
     * @param tokenService Token 服务
     * @return 过滤器注册Bean
     */
    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilter(TokenService tokenService) {
        RequestFilter requestFilter = new RequestFilter(tokenService);
//        requestFilter.addExcludeUrlPatterns(
//                "/login",
//                "/logout",
//                "/register"
//        );

        FilterRegistrationBean<RequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(requestFilter);
        filterRegistrationBean.addUrlPatterns(
                "/api/*"
        );
        filterRegistrationBean.setOrder(0);

        return filterRegistrationBean;
    }

    /**
     * 对需要管理员权限的请求进行过滤，验证Token，验证权限。
     *
     * @param userService  User 服务
     * @param tokenService Token 服务
     * @return 过滤器注册Bean
     */
    @Bean
    public FilterRegistrationBean<RoleFilter> adminFilter(UserService userService,
                                                          TokenService tokenService) {
        RoleFilter adminFilter = new RoleFilter(userService, tokenService);

        FilterRegistrationBean<RoleFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(adminFilter);
        filterRegistrationBean.addUrlPatterns(
                "/api/dept/a/*",
                "/api/document/a/*",
                "/api/employee/a/*",
                "/api/job/a/*",
                "/api/notice/a/*",
                "/api/user/a/*"
        );
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

    /**
     * 配置静态资源
     *
     * @param registry 注册类对象
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 允许跨域访问
     *
     * @param registry 注册类对象
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    /**
     * 配置 fremmarker
     *
     * @return new FreeMarkerConfigurer
     */
    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths("classpath:/templates/");
        configurer.setDefaultEncoding("UTF-8");

        freemarker.template.Configuration configuration = configurer.createConfiguration();
        configuration.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);

        configurer.setConfiguration(configuration);
        return configurer;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
//        registration.addPathPatterns("/*");
//        registration.excludePathPatterns("/error");
    }

    /**
     * 配置视图解析器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setSuffix(CsiConst.SUFFIX_FTL);
        resolver.setContentType("text/html; charset=UTF-8");
        registry.viewResolver(resolver);
    }
}
