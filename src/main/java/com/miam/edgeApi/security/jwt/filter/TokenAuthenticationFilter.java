package com.miam.edgeApi.security.jwt.filter;


import com.miam.edgeApi.security.Utils.Utilities;
import com.miam.edgeApi.security.jwt.provider.JwtTokenProvider;
import com.miam.edgeApi.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public TokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Obtén el token del encabezado de la solicitud
            var token = Utilities.getJwtTokenFromRequest(request);

            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                // Extrae el username (sub) desde el token
                var username = jwtTokenProvider.getUsernameFromToken(token);

                // Crea un UserDetails básico si el token es válido, sin cargar desde la base de datos
                var userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(username)
                        .password("") // No se necesita la contraseña aquí
                        .build();

                // Establece la autenticación en el SecurityContext
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("[!] - No se pudo establecer la autenticación en el SecurityContext -> " + ex.getMessage());
        }

        //continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
