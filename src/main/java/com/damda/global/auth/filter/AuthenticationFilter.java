package com.damda.global.auth.filter;

import com.damda.global.exception.AuthenticationFailException;
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
            throw new AuthenticationFailException("인증에 실패하였습니다.");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return requestURI.startsWith("/login") || requestURI.startsWith("/signup")
                || requestURI.startsWith("/swagger") || requestURI.startsWith("/v3") || requestURI.startsWith("/api-docs");
    }
}