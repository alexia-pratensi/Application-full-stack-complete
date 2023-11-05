// package com.openclassrooms.mddapi.security;

// import java.util.Arrays;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http.cors();
// }

// @Bean
// public CorsConfigurationSource corsConfigurationSource() {
// final CorsConfiguration configuration = new CorsConfiguration();
// configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
// configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS",
// "DELETE", "PUT"));
// configuration.setAllowCredentials(true);
// configuration.setAllowedHeaders(Arrays.asList("Authorization",
// "Cache-Control", "Content-Type"));
// final UrlBasedCorsConfigurationSource source = new
// UrlBasedCorsConfigurationSource();
// source.registerCorsConfiguration("/**", configuration);
// return source;
// }

// }
