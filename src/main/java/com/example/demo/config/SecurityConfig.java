package com.example.demo.config;

import com.example.demo.filter.JWTChecker;
import com.example.demo.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/", "/user/signup", "/user/list", "/login", "/logout").permitAll()
                    //.requestMatchers("/user/info","/user/info/*", "/user/order", "/user/order/*").hasAnyRole("USER")
                    .anyRequest().permitAll();
        });
        // 세션을 사용하지 않도록 설정
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.addFilterAt(new LoginFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTChecker(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
