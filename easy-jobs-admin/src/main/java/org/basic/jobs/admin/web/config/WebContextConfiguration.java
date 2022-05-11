package org.basic.jobs.admin.web.config;

import org.basic.jobs.admin.web.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebContextConfiguration extends WebMvcConfigurerAdapter {

    // TODO use Spring Security

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/home")
                .addPathPatterns("/jobs")
                .addPathPatterns("/executions")
                .addPathPatterns("/requests/**")
        ;
    }

}
