package com.ribaso.userservice.port.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {        
        http.oauth2Login(oauth2Login -> oauth2Login
                .tokenEndpoint(Customizer.withDefaults())
                .userInfoEndpoint(Customizer.withDefaults())
                .defaultSuccessUrl("http://userservice:8081/user")
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            )

            .authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.DELETE, "/user/**")
                    .hasRole("admin")
                .requestMatchers("/unauthenticated", "/oauth2/**")
                    .permitAll()
                .anyRequest()
                    .hasAnyRole("user", "admin")
            )

            .logout(logout -> logout
                .logoutSuccessUrl("http://keycloak:8080/realms/ribaso/protocol/openid-connect/logout?redirect_uri=http://userservice:8081/")
            );
        
        return http.build();
    }

    @Bean
    public CustomOidcUserService customOidcUserService() {
        return new CustomOidcUserService();
    }
}
