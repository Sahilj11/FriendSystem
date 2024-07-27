package com.example.credit.security.filter;

import com.example.credit.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** JwtFilter */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && JwtUtil.validHeader(authHeader)) {
            String token = authHeader.substring(7).trim();
            if (!JwtUtil.isTokenExpired(token)) {
                String email = JwtUtil.extractEmail(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    log.info("EMAIL  " + email);
                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null,
                                userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } catch (Exception e) {
                        log.warn("Issue in loading username or setting authentication context " + e.getMessage());
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
