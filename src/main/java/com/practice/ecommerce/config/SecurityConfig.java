package com.practice.ecommerce.config;

import jakarta.servlet.http.HttpServletResponse;

import javax.security.sasl.AuthenticationException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth.requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
                .requestMatchers(AppConstants.ADMIN_URLS).hasAnyAuthority("ADMIN")
                .requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER", "ADMIN")
                .anyRequest()
                .authenticated());
        // http.csrf().disable().authorizeHttpRequests()
        // .requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
        // .requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER", "ADMIN")
        // .requestMatchers(AppConstants.ADMIN_URLS).hasAnyAuthority("ADMIN")
        // .anyRequest()
        // .authenticated()
        // .and()
        // .exceptionHandling().authenticationEntryPoint(
        // ((request, response, authException) ->
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
        // "UnAuthorized")))
        // .and()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.csrf(csrf -> csrf.disable()).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
