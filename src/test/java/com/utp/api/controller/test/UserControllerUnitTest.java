package com.utp.api.controller.test;

import com.utp.api.application.request.dto.LoginRequestDTO;
import com.utp.api.application.request.dto.UserRegisterRequestDTO;
import com.utp.api.application.response.dto.ApiResponseDTO;
import com.utp.api.application.response.dto.UserResponseDTO;
import com.utp.api.application.service.UserService;
import com.utp.api.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        // DTO para registrar usuario
        UserRegisterRequestDTO registerRequest = new UserRegisterRequestDTO("username", "email@example.com", "Password123");

        // Simular comportamiento del servicio
        UserResponseDTO response = new UserResponseDTO(1L, "username", "email@example.com");
        when(userService.registerUser(any(UserRegisterRequestDTO.class))).thenReturn(response);

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<UserResponseDTO>> result = userController.registerUser(registerRequest);

        // Verificar el resultado
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Usuario registrado exitosamente", result.getBody().getMessage());
        assertEquals("username", result.getBody().getData().getUsername());
        assertEquals("email@example.com", result.getBody().getData().getEmail());
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        // DTO con nombre de usuario inválido
        UserRegisterRequestDTO registerRequest = new UserRegisterRequestDTO("ab", "email@example.com", "Password123");

        // Simular comportamiento del servicio (arroja una excepción)
        when(userService.registerUser(any(UserRegisterRequestDTO.class))).thenThrow(new IllegalArgumentException("Error de validación"));

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<UserResponseDTO>> result = userController.registerUser(registerRequest);

        // Verificar el resultado
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error de validación", result.getBody().getMessage());
    }

    @Test
    public void testLoginUser_Success() {
        // DTO para iniciar sesión
        LoginRequestDTO loginRequest = new LoginRequestDTO("email@example.com", "Password123");

        // Simular comportamiento del servicio
        when(userService.loginUser(any(LoginRequestDTO.class))).thenReturn("fake-jwt-token");

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<String>> result = userController.loginUser(loginRequest);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Inicio de sesión exitoso", result.getBody().getMessage());
        assertEquals("fake-jwt-token", result.getBody().getData());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        // DTO para iniciar sesión con credenciales inválidas
        LoginRequestDTO loginRequest = new LoginRequestDTO("email@example.com", "wrongPassword");

        // Simular comportamiento del servicio (arroja una excepción)
        when(userService.loginUser(any(LoginRequestDTO.class))).thenThrow(new IllegalArgumentException("Credenciales inválidas"));

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<String>> result = userController.loginUser(loginRequest);

        // Verificar el resultado
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Credenciales inválidas", result.getBody().getMessage());
    }
}
