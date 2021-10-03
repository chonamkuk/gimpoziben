package kr.co.kimpoziben.config;

import kr.co.kimpoziben.config.auth.AjaxAwareAuthenticationEntryPoint;
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
                .antMatchers("/order/**").hasAnyRole(UserRole.USER.name(), UserRole.USER.ADMIN.name(), UserRole.USER.SUPERADMIN.name())
                .antMatchers("/admin/**").hasAnyRole(UserRole.USER.ADMIN.name(), UserRole.USER.SUPERADMIN.name())
                .anyRequest().permitAll() // 기본적으로 permitAll
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(ajaxAwareAuthenticationEntryPoint("/oauth2/authorization/naver"))
             .and()
                 .formLogin()
                    .loginPage("/oauth2/authorization/naver")
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
        ;
    }


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

//    @Bean
//    public ReadableRequestWrapperFilter readableRequestWrapperFilter() {
//        return new ReadableRequestWrapperFilter();
//    }

    /**인증되지 않은 요청중 AJAX요청일 경우 403으로 응답, AJAX요청이 아닐 경우 login으로 리다이렉트
     * @param url
     * @return
     */
    private AjaxAwareAuthenticationEntryPoint ajaxAwareAuthenticationEntryPoint(String url) {
        return new AjaxAwareAuthenticationEntryPoint(url);
    }
}
