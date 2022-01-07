package com.sp.fc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled= true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.builder().username("user2")
                                        .password(passwordEncoder().encode("2222"))
                                        .roles("USER"))
                .withUser(User.builder().username("admin")
                                        .password(passwordEncoder().encode("3333"))
                                        .roles("ADMIN"));
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**");

        http.authorizeRequests((requests) ->
                requests.antMatchers("/").permitAll() // 로그인 안했어도 홈페이지는 permit을 허용해주겠다.
                        .anyRequest().authenticated()); //어떤 리퀘스트든 인증받은 상태에서 접근해라.
        http.formLogin();
        http.httpBasic();//super.configure에서 가져옴
    }
}
