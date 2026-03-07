package com.example.spring_mini_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    //FilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF
                .authorizeHttpRequests(auth -> auth
                        //public end-point
                        .requestMatchers(
                                HttpMethod.POST, "/mini-shop/user/register",
                                "/mini-shop/user/login"
                        ).permitAll()

                        //User Permission & Admin
                        .requestMatchers(
                                HttpMethod.GET,"/mini-shop/products",
                                "/mini-shop/products/{id}",
                                "/mini-shop/order",
                                "/mini-shop/order/{id}"
                        ).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(
                                HttpMethod.POST, "/mini-shop/order"
                        ).hasAnyRole("USER","ADMIN")

                        //ADMIN Only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/mini-shop/category",
                                "/mini-shop/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/mini-shop/category/{id}",
                                "/mini-shop/products/{id}",
                                "/mini-shop/order/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/mini-shop/category/{id}",
                                "/mini-shop/products/{id}",
                                "/mini-shop/order/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
