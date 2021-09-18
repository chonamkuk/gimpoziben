package kr.co.kimpoziben.config;

import kr.co.kimpoziben.config.auth.LoginUserAragumentResolver;
import kr.co.kimpoziben.interceptor.LoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserAragumentResolver loginUserAragumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserAragumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor())
            .addPathPatterns("*/*.do");
    }

    @Bean
    public LoggerInterceptor loggerInterceptor() { return new LoggerInterceptor(); }
}
