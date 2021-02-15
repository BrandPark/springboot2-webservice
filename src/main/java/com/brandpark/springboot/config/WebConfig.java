package com.brandpark.springboot.config;


import com.brandpark.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;  //HandlerMethodArgumentResolver

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //HandlerMethodArgumentResolver 는 항상 WebMvcCnfigurer의 이 메서드를 통해 추가해야 한다.
        resolvers.add(loginUserArgumentResolver);
    }
}
