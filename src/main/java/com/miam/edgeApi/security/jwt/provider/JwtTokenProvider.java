package com.miam.edgeApi.security.jwt.provider;

import com.miam.edgeApi.security.Utils.Utilities;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    //obtiene una propiedad del application.properties
    @Value("${app.jwt-secret}")
    private String secret;

    @Value("${app.jwt-expiration-min}")
    private long expiration;

    public String generateToken(Authentication authentication) {
        var authenticatedUser = (User) authentication.getPrincipal();

        //in minutes
        var expiryDate = new Date(new Date().getTime() + expiration * 60_000);

        //construye el token JWT
        return Jwts.builder()
                .subject(authenticatedUser.getUsername()) //se usa el username como subject
                .issuedAt(new Date()) //fecha de emisión
                .expiration(expiryDate) //fecha de expiración
                .signWith(Utilities.getKey(secret)) //firma el token
                .claim("roles", Utilities.getRoles(authenticatedUser)) //agrega los roles
                .compact(); //construye el token
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Utilities.getKey(secret)) //verifica la firma
                .build() //construye el parser
                .parseSignedClaims(token) //parsea el token
                .getPayload(); //obtiene los claims (payload)

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Utilities.getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (MalformedJwtException ex) {
            log.warn("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.warn("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.warn("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims string is empty");
        }

        return false;
    }
}
