package com.accesspoint.Utils;

import com.accesspoint.Security.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AppUserDetailsService appUserDetailsService;
    private final JwtUtil jwtUtil;

    public final List<String> PUBLIC_URLS = List.of("/login", "/register", "/send-reset-otp", "reset-password", "/logout");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if(PUBLIC_URLS.contains(path)){
            filterChain.doFilter(request,response);
            return;
        }

        String jwt = null;
        String email = null;

        // 1. cheak the autherisation header
        final String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
        }

        // 2. if not found in header, cheak cookies
        if(jwt == null){
            Cookie[] cookie = request.getCookies();
            if(cookie != null){
                for(Cookie c : cookie){
                    if(c.getName().equals("jwt")){
                        jwt = c.getValue();
                        break;
                    }
                }
            }
        }

        // 3. validate the token and set the security contex
        if(jwt != null){
            email = jwtUtil.extractEmail(jwt);
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);
                if(jwtUtil.validateTooken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
