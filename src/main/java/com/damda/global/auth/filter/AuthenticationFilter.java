package com.damda.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return isStaticResource(requestURI) || isUnsecuredPath(requestURI);
    }

    private boolean isStaticResource(String requestURI) {
        return requestURI.startsWith("/css/") || requestURI.startsWith("/js/") ||
                requestURI.startsWith("/images/") || requestURI.startsWith("/fonts/") ||
                requestURI.startsWith("/favicon.ico") || requestURI.startsWith("/static") ||
                requestURI.startsWith("/resources");
    }

    private boolean isUnsecuredPath(String requestURI) {
        return requestURI.startsWith("/login") || requestURI.startsWith("/signup") ||
                requestURI.startsWith("/swagger") || requestURI.startsWith("/v3") ||
                requestURI.startsWith("/api-docs");
    }
}
