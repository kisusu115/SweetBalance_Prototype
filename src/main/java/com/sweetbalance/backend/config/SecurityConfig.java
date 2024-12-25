package com.sweetbalance.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.sweetbalance.backend.enums.user.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Role Hierarchy 필요하다면 추가

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/", "/login").permitAll()
//                        .requestMatchers("/admin").hasRole(ROLE_ADMIN.getValue())
//                        .requestMatchers("/users/**").hasAnyRole(ROLE_ADMIN.getValue(), ROLE_USER.getValue())
                        .anyRequest().authenticated()
                );

        httpSecurity
                .csrf((auth) -> auth.disable());

        return httpSecurity.build();
    }
}
