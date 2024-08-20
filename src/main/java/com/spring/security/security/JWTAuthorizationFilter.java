package com.spring.security.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.spring.security.services.IJWTUtilityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    //@Autowired inyecta dependencias automáticamente en los componentes gestionados
    //por el contenedor.
    @Autowired
    IJWTUtilityService jwtUtilityService;

    //Creamos el constructor
    public JWTAuthorizationFilter(IJWTUtilityService jwtUtilityService) {
        this.jwtUtilityService = jwtUtilityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtiene el valor del header "Authorization" de la solicitud HTTP
        String header = request.getHeader("Authorization");

        // Verifica si el header "Authorization" es nulo o no empieza con "Bearer "
        if(header == null || !header.startsWith(("Bearer "))){
            filterChain.doFilter(request, response);
            return;
        }//fin if

        //Extraemos el Bearer del token
        String token = header.substring(7);//Primeros 7 caracteres y hasta el final

        //Hacemos un try cath
        try{
            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null , Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }//fin try catch

        //Continúa con el siguiente filtro en la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
