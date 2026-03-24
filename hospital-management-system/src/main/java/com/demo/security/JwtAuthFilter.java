package com.demo.security;

import com.demo.entity.User;
import com.demo.repository.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepo userRepo;
    private final AuthUtils authUtils;
//    private final JwtAuthFilter jwtAuthFilter;
//    private final HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("incoming request : {}", request.getRequestURI());
//        try {
            final String authorizationHeader = request.getHeader("Authorization"); // autherisationHeader == requestTokenHeader
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }

            String token = authorizationHeader.split("Bearer ")[1];
            String username = authUtils.getUsernameFromToken(token);

            if(username == null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = userRepo.findByUsername(username).orElseThrow();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            filterChain.doFilter(request,response);
//        }catch (Exception e){
//            exceptionResolver.resolveException(request,response,null, e);
//        }
    }
}
