package com.cryptoexchange.cryptoexchange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){

        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/welcome").setViewName("welcom");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/cpanel").setViewName("cpanel");
        registry.addViewController("/loginerror").setViewName("loginerror");
        registry.addViewController("/logoutsuccess").setViewName("logoutsuccess");
        registry.addViewController("/loginsuccess").setViewName("loginsuccess");
        registry.addViewController("/403").setViewName("403");
    }
    
}
