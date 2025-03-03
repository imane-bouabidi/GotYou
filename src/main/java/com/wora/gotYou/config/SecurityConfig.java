package com.wora.gotYou.config;

import com.wora.gotYou.security.JwtAuthenticationFilter;
import com.wora.gotYou.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final com.wora.gotYou.security.JwtTokenProvider JwtTokenProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        .requestMatchers("/api/requests/**").hasRole("STUDENT")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/operator/**").hasRole("OPERATOR")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtAuthenticationFilter(JwtTokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (request, response, accessDeniedException) -> {
//            response.setStatus(403);
//            response.getWriter().write("Accès refusé : vous n'avez pas les autorisations nécessaires.");
//        };
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
