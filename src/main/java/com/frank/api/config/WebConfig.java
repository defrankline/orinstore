package com.frank.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/bower_components/**",
                "/img/**",
                "/css/**",
                "/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/bower_components/",
                        "classpath:/static/img/",
                        "classpath:/static/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        ViewControllerRegistration r = registry.addViewController("/login");
        r.setViewName("login");
    }
}