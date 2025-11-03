package com.billtel.calidad.facturacion_pymes.layer.auth;


import com.billtel.calidad.facturacion_pymes.layer.auth.filters.JwtAuthenticationFilter;
import com.billtel.calidad.facturacion_pymes.layer.auth.filters.JwtValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final String EMPRESA_BY_ID = "/empresa/{id}";
    private static final String PRODUCTO_ALL = "/producto/**";

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/usuario").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuario/{id}").hasAnyRole(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/usuario").hasRole(ROLE_ADMIN)

                        // Empresas
                        .requestMatchers(HttpMethod.GET, "/empresa").hasAnyRole(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, EMPRESA_BY_ID).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/empresa").hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, EMPRESA_BY_ID).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, EMPRESA_BY_ID).hasRole(ROLE_ADMIN)

                        // Productos
                        .requestMatchers(HttpMethod.GET, PRODUCTO_ALL).hasAnyRole(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, PRODUCTO_ALL).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, PRODUCTO_ALL).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, PRODUCTO_ALL).hasRole(ROLE_ADMIN)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtValidationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);// NOSONAR - CSRF is disabled intentionally because JWT is stateless authentication

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}


