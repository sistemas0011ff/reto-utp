package com.utp.api.shared.util;

import com.utp.api.application.response.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                "Acceso denegado: No tienes permisos para acceder a este recurso.",
                HttpStatus.FORBIDDEN.value(),
                null
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        response.getWriter().write("{\"status\": " + HttpStatus.FORBIDDEN.value() + ", "
                                   + "\"message\": \"" + responseDTO.getMessage() + "\", "
                                   + "\"data\": null}");
        response.getWriter().flush();
    }
}
