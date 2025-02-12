package com.example.demo.config;

import com.example.demo.filter.JWTChecker;
import com.example.demo.filter.LoginFilter;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    // 로그인 필터를 swagger-ui에 수동 등록
    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointsCustomizer(ApplicationContext applicationContext) {
        FilterChainProxy springSecurityFilterChain = applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
        return (openApi -> {
            for (SecurityFilterChain securityFilterChain : springSecurityFilterChain.getFilterChains()) {
                // 특정 필터체인의 특정 필터를 가져옴
                Optional<LoginFilter> filter = securityFilterChain.getFilters().stream()
                        .filter(LoginFilter.class::isInstance)
                        .map(LoginFilter.class::cast)
                        .findAny();
                if (filter.isPresent()) {
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperty("username", new StringSchema())
                            .addProperty("password", new StringSchema());
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType("application/json", new MediaType().schema(schema))
                    );
                    operation.setRequestBody(requestBody);

                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()), new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                    operation.setResponses(apiResponses);

                    // 수동으로 컨트롤러처럼 API URL 항목 만들어주고 summary 추가
                    operation.addTagsItem("member-controller");
                    operation.summary("로그인");
                    PathItem pathItem = new PathItem().post(operation);
                    openApi.getPaths().addPathItem("/login", pathItem);
                }

            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable); // 사실 이러면 안 되는데...
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/", "/user/signup", "/user/list", "/login", "/logout").permitAll()
                    //.requestMatchers("/user/info","/user/info/*", "/user/order", "/user/order/*").hasAnyRole("USER")
                    .anyRequest().permitAll();
        });
        // 로그아웃
        http.logout(httpSecurityLogoutConfigurer -> {
            httpSecurityLogoutConfigurer.logoutSuccessUrl("/").permitAll()
                    .deleteCookies("JSESSIONID", "ATOKEN"); // 세션을 레거시 인가 수단으로 쓴 경우 대비
        });
        // 세션을 사용하지 않도록 설정
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.addFilterAt(new LoginFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTChecker(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
