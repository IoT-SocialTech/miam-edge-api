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
            var token = Utilities.getJwtTokenFromRequest(request);

            //valida el token
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                //se obtiene el username del token
                var username = jwtTokenProvider.getUsernameFromToken(token);
                //carga los detalles del usuario autenticado para establecer la seguridad
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                //crea un objeto que representa la autenticación del usuario
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //establece los detalles de autenticación adicionales
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //establece la seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("[!] - No se pudo establecer la autenticación en el SecurityContext -> " + ex.getMessage());
        }

        //continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
