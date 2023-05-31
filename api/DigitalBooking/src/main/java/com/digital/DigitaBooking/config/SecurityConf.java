package com.digital.DigitaBooking.config;


import com.digital.DigitaBooking.jwt.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.digital.DigitaBooking.models.entities.Role.ADMIN;
import static com.digital.DigitaBooking.models.entities.Role.USER;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                                //Rutas de autorizaciones
                                .requestMatchers(HttpMethod.GET, "/welcome/anyone").permitAll()
                                .requestMatchers(HttpMethod.GET, "/welcome/user")
                                .hasAnyAuthority(USER.name(), ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/sign-up").permitAll()
                                .requestMatchers(HttpMethod.GET, "/welcome/admin").hasAuthority(ADMIN.name())
                                //Rutas de tours
                                .requestMatchers(HttpMethod.GET, "/tours").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tours/byCategory/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/tours").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/tours/{id}").hasAuthority(ADMIN.name())
                                //Rutas de categorias
                                .requestMatchers(HttpMethod.POST,"/category").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/category").permitAll()
                                .requestMatchers(HttpMethod.GET,"/category/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/category/{id}").hasAnyAuthority(ADMIN.name())
                                .anyRequest().authenticated())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8000"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
