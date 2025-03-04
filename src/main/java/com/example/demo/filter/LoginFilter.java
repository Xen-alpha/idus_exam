package com.example.demo.filter;

import com.example.demo.model.UserEntity;
import com.example.demo.model.user.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("인증 시행 중");
        UsernamePasswordAuthenticationToken token;
        try {
            LoginRequest user = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
            System.out.println(user.getUsername() + " " + user.getPassword());
            token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        UserEntity user = (UserEntity) authResult.getPrincipal();
        String token = JWTChecker.createToken(user.getIdx(), user.getNickname(), user.getRole());
        ResponseCookie cookie = ResponseCookie.from("TOKEN", token)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(24*3600)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
