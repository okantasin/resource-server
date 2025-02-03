package com.template.resourceserver.security;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @PostConstruct
    public void init() {
        System.out.println("🚀 SecurityConfig YÜKLENDİ!");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, KeycloakRoleConverter keycloakRoleConverter) throws Exception {
        System.out.println("🚀 SecurityFilterChain YÜKLENDİ!");

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/status/check").hasRole("developer")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter(keycloakRoleConverter)))
                )
                .build();


    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(KeycloakRoleConverter keycloakRoleConverter) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);
        return converter;
    }
}
