package com.oauth.config;

import com.oauth.dto.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Component
public class CustomConverter implements Converter<Jwt, Collection<SimpleGrantedAuthority>> {
    @Override
    public Collection convert(Jwt source) {
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        List<SimpleGrantedAuthority> roleList = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();

        return roleList;
    }
}
