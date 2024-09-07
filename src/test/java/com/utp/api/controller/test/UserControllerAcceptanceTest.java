package com.utp.api.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.api.application.request.dto.LoginRequestDTO;
import com.utp.api.application.request.dto.UserRegisterRequestDTO;
import com.utp.api.application.response.dto.UserResponseDTO;
import com.utp.api.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser_UsernameIsString() throws Exception {
       
        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO("string", "test@example.com", "Password123");

        when(userService.registerUser(any())).thenThrow(new IllegalArgumentException("El nombre de usuario no puede ser 'string'"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"))
                .andExpect(jsonPath("$.data.username").value("El nombre de usuario no puede ser 'string'"));
    }

    @Test
    public void testRegisterUser_InvalidEmail() throws Exception {
        
        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO("validUsername", "invalidEmail", "Password123");

        
        when(userService.registerUser(any())).thenThrow(new IllegalArgumentException("El formato del correo electrónico es inválido"));

        
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"))
                .andExpect(jsonPath("$.data.email").value("El formato del correo electrónico es inválido"));
    }
    
    @Test
    public void testRegisterUser_Success() throws Exception {
        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO("validUsername", "test@example.com", "Password123");

        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "validUsername", "test@example.com");

        when(userService.registerUser(any())).thenReturn(userResponseDTO);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"))
                .andExpect(jsonPath("$.data.username").value("validUsername"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }

 
    @Test
    public void testLoginUser_Success() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("validUsername", "Password123");

        when(userService.loginUser(any())).thenReturn("mocked-token");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso"))
                .andExpect(jsonPath("$.data").value("mocked-token"));
    }

    @Test
    public void testLoginUser_InvalidCredentials() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("invalidUsername", "wrongPassword");

        when(userService.loginUser(any())).thenThrow(new IllegalArgumentException("Usuario o contraseña incorrecta"));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Usuario o contraseña incorrecta"))
                .andExpect(jsonPath("$.data").isEmpty()); 
    }

}
