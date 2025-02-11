package com.example.demo.filter;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JWTChecker extends OncePerRequestFilter {
    // TODO: 아래 어노테이션이 작동하게 할 것
    // @Value("${jwtSecret.expiry}")
    private final static int expiry = 216000;
    // TODO: 아래 어노테이션이 작동하게 할 것 : 이 상태로는 Secret이 Authentication 때마다 바뀐다.
    // @Value("${jwtSecret.key}")
    private final static String secret = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");

    public static String createToken(Long Idx, String username, String role) {
      Map<String, Object> datamap = new HashMap<>();
      datamap.put("username", username);
      datamap.put("role", role);
      datamap.put("userIdx", Idx);
      System.out.println("Role inside Token: " + role);
      return Jwts.builder().signWith(SignatureAlgorithm.HS256, secret).setClaims(datamap).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + expiry)).compact();
    }

    public static Boolean isValid(String token) {
      try {
          Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
      } catch (Exception e) {
          return false;
      }
      return true;
    }
    public static UserEntity getUser(String token) {
      try {
          Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJwt(token).getBody();
          return new UserEntity(claims.get("userIdx", Long.class), claims.get("username", String.class), null, null, null, null, null, null, claims.get("role", String.class), null);
      } catch (Exception e) {
          return null;
      }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TOKEN")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token != null) {
            if (isValid(token)) {
                UserEntity user = getUser(token);
                if (user != null) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
