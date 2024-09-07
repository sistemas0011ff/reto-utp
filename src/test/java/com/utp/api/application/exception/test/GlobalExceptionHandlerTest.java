package com.utp.api.application.exception.test;

import com.utp.api.application.exception.GlobalExceptionHandler;
import com.utp.api.application.response.dto.ApiResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
    }

    @Test
    public void testHandleAuthenticationException() {
        AuthenticationException ex = mock(AuthenticationException.class);
        when(ex.getMessage()).thenReturn("Invalid credentials");

        ResponseEntity<ApiResponseDTO<String>> response = globalExceptionHandler.handleAuthenticationException(ex, webRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("No autenticado: Invalid credentials", response.getBody().getMessage());
    }

    @Test
    public void testHandleAccessDeniedException() {
        AccessDeniedException ex = mock(AccessDeniedException.class);
        when(ex.getMessage()).thenReturn("Access denied");

        ResponseEntity<ApiResponseDTO<String>> response = globalExceptionHandler.handleAccessDeniedException(ex, webRequest);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Acceso denegado: Access denied", response.getBody().getMessage());
    }

    @Test
    public void testHandleValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>()); // empty errors for simplicity

        ResponseEntity<ApiResponseDTO<Map<String, String>>> response = globalExceptionHandler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de validación", response.getBody().getMessage());
    }

    @Test
    public void testHandleConstraintViolationException() {
        ConstraintViolationException ex = new ConstraintViolationException("Field validation failed", null);

        ResponseEntity<ApiResponseDTO<String>> response = globalExceptionHandler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de validación: Field validation failed", response.getBody().getMessage());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ApiResponseDTO<String>> response = globalExceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argumento ilegal: Invalid argument", response.getBody().getMessage());
    }

    @Test
    public void testHandleGeneralException() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ApiResponseDTO<String>> response = globalExceptionHandler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error del servidor: Unexpected error", response.getBody().getMessage());
    }
}