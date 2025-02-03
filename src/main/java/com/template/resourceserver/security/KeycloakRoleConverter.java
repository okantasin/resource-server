package com.template.resourceserver.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> claims = jwt.getClaims();

        if (claims.containsKey("realm_access")) {
            Object realmAccessObj = claims.get("realm_access");

            if (realmAccessObj instanceof Map) {
                Map<String, Object> realmAccessMap = (Map<String, Object>) realmAccessObj;

                if (realmAccessMap.containsKey("roles")) {
                    Object rolesObj = realmAccessMap.get("roles");

                    if (rolesObj instanceof List<?>) {
                        List<?> rolesList = (List<?>) rolesObj;

                        for (Object role : rolesList) {
                            if (role instanceof String) {
                                String roleName = "ROLE_" + role; // Spring Security için ROLE_ ekle
                                authorities.add(new SimpleGrantedAuthority(roleName));
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Kullanıcının Yetkileri: " + authorities); // Yetkileri logla
        return authorities;
    }
}