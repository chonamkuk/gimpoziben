package kr.co.kimpoziben;

import kr.co.kimpoziben.interceptor.LoggerInterceptor;
import kr.co.kimpoziben.interceptor.ReadableRequestWrapperFilter;
import kr.co.kimpoziben.util.AES256Util;
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
@DependsOn(value = "propertyConfig")
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    private PropertyConfig propertyConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable() // cors 허용
                .csrf().disable() // csrf 허용
                .headers().contentTypeOptions().disable() // IE 이미지로딩
                .frameOptions().disable();

        // admin
//        http.authorizeRequests()
//                .antMatchers("/login.do").permitAll()
//                .antMatchers("/admin/**").hasAuthority("SUPER_ADMIN")
//                .anyRequest().permitAll();
//
//        http.formLogin()
//                .loginPage("/login.do")
//                .loginProcessingUrl("/loginProc.do")
//                .usernameParameter("idAccount")
//                .passwordParameter("passwordAccount")
//                .defaultSuccessUrl("/main.do");
//        http.logout()
//                .logoutUrl("/logout.do")
//                .logoutSuccessUrl("/main.do")
//                .invalidateHttpSession(true);
//        http.exceptionHandling().accessDeniedPage("/accessDenied.do");
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
    public LoggerInterceptor loggerInterceptor() { return new LoggerInterceptor(); }

    @Bean
    public ReadableRequestWrapperFilter readableRequestWrapperFilter() {
        return new ReadableRequestWrapperFilter();
    }
}
