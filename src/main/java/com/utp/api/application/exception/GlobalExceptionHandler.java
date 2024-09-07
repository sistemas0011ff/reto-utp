package com.utp.api.application.exception;

import com.utp.api.application.response.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepción de autenticación (no autenticado)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                "No autenticado: " + ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Manejo de excepción de acceso denegado (falta de permisos)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                "Acceso denegado: " + ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Manejo de excepciones para validación de campos en solicitudes (p. ej., DTOs inválidos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Itera sobre los errores de validación de campo y los añade al mapa
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponseDTO<Map<String, String>> response = new ApiResponseDTO<>(
                "Error de validación",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepción para errores de validación a nivel de constraints (p. ej., @NotNull, @Size, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                "Error de validación: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepción para argumentos ilegales
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                "Argumento ilegal: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones generales (cualquier excepción no controlada)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleGeneralException(Exception ex) {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                "Error del servidor: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
