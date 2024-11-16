package com.miam.edgeApi.security.Utils;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.List;

@Slf4j
public class Utilities {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    static public String getJwtTokenFromRequest(HttpServletRequest request) {
        //obtiene el token JWT desde el header
        String jwtTokenFromHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(jwtTokenFromHeader) && jwtTokenFromHeader.startsWith(BEARER_PREFIX)) {
            return jwtTokenFromHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }


    static public List<String> getRoles(User authenticatedUser) {
        return authenticatedUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    static public SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

}