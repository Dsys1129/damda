package com.damda.global.auth.filter;


import com.damda.global.exception.AuthenticationFailException;
import com.damda.global.exception.CustomException;
import com.damda.global.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthExceptionFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        } catch (AuthenticationFailException e){
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, CustomException customException) throws IOException {
        response.setStatus(customException.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        ExceptionResponse exceptionResponse = new ExceptionResponse(customException);
        objectMapper.writeValue(response.getWriter(), exceptionResponse);
    }
}
