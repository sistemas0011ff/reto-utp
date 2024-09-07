package com.utp.api.shared.util;

import com.utp.api.application.response.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        
        ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                "No estás autenticado. Por favor, envía un token válido en el encabezado 'Authorization'.",
                HttpStatus.UNAUTHORIZED.value(),
                null
        );

       
        response.getWriter().write("{\"status\": " + HttpStatus.UNAUTHORIZED.value() + ", "
                + "\"message\": \"" + responseDTO.getMessage() + "\", "
                + "\"data\": null}");
        response.getWriter().flush();
    }
}
