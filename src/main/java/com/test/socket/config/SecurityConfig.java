package com.test.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/ws/chat").permitAll());
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/gs-guide-websocket").permitAll());
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/messages").permitAll());
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/join/**").permitAll());
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers(PERMIT_URL_ARRAY).permitAll());

//        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/app/chat/message").permitAll());
//        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/topic/chat/room/**").permitAll());

        httpSecurity.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
