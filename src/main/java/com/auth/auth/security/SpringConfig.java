package com.auth.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.auth.auth.security.filter.JWTAuthentication;
import com.auth.auth.security.filter.JWTAuthorization;

@Configuration
public class SpringConfig {

    private CustomAuthenticationManager customAuthenticationManager;

    public SpringConfig(CustomAuthenticationManager customAuthenticationManager){
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JWTAuthentication jwtAuthentication = new JWTAuthentication(customAuthenticationManager);
        jwtAuthentication.setFilterProcessesUrl("/login");

        http
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
        .authorizeHttpRequests(request -> request
            .requestMatchers("/h2/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
        )
        .addFilter(jwtAuthentication)
        .addFilterAfter(new JWTAuthorization(), JWTAuthentication.class)
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
