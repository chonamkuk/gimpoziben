package kr.co.kimpoziben.config;

import kr.co.kimpoziben.config.auth.UserRole;
import kr.co.kimpoziben.interceptor.LoggerInterceptor;
import kr.co.kimpoziben.interceptor.ReadableRequestWrapperFilter;
import kr.co.kimpoziben.config.auth.CustomOAuth2UserService;
import kr.co.kimpoziben.util.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import java.io.UnsupportedEncodingException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@DependsOn(value = "propertyConfig")
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    private PropertyConfig propertyConfig;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .headers()
                    .contentTypeOptions().disable()
                    .frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/api/v1/**").hasAnyRole(UserRole.USER.name(), UserRole.USER.ADMIN.name(), UserRole.USER.SUPERADMIN.name())
                .antMatchers("/order/**").hasAnyRole(UserRole.USER.name(), UserRole.USER.ADMIN.name(), UserRole.USER.SUPERADMIN.name())
                .antMatchers("/admin/**").hasAnyRole(UserRole.USER.ADMIN.name(), UserRole.USER.SUPERADMIN.name())
                .anyRequest().permitAll() // 기본적으로 permitAll
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("manager")
//                .password(passwordEncoder().encode("1111"))
//                .roles("ADMIN");
//    }

    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(loggerInterceptor())
        //    .addPathPatterns("/**/delete.do");
        //        .addPathPatterns("/**/detail.do");
        //.excludePathPatterns("/test/**/")
        //.excludePathPatterns("/users/login"); //로그인 쪽은 예외처리를 한다.
    //}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AES256Util aes256Util() throws UnsupportedEncodingException {
        return new AES256Util(propertyConfig.getAesutilIv());
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Bean
    public ReadableRequestWrapperFilter readableRequestWrapperFilter() {
        return new ReadableRequestWrapperFilter();
    }
}
