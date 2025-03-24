package com.wora.gotYou.config;

import com.wora.gotYou.security.JwtAuthenticationFilter;
import com.wora.gotYou.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register/**", "/api/auth/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/upload-profile-image").hasAnyRole("STUDENT", "DONOR")

                        .requestMatchers(HttpMethod.GET, "/api/requests").hasAnyRole("STUDENT", "DONOR", "ADMIN")

//                        .requestMatchers(HttpMethod.POST, "/api/requests/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/requests/{id}").hasRole("STUDENT")

                        .requestMatchers(HttpMethod.DELETE, "/api/requests/{id}").hasRole("STUDENT")

                        .requestMatchers(HttpMethod.GET, "/api/requests/search").hasAnyRole("STUDENT", "DONOR")

                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasRole("DONOR")
//                        .requestMatchers(HttpMethod.GET, "/api/donations/create-checkout-session").hasRole("DONOR")
                        .requestMatchers(HttpMethod.GET, "/api/donations/create-checkout-session/**").hasRole("DONOR")

                        .requestMatchers(HttpMethod.GET, "/api/users/current").authenticated()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/admin/student-requests/status/**").hasRole("ADMIN")

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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
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
