package com.utp.api.application.service.test;

import com.utp.api.application.command.CreateUserCommand;
import com.utp.api.application.query.FindUserByUsernameQuery;
import com.utp.api.application.request.dto.UserRegisterRequestDTO;
import com.utp.api.application.request.dto.LoginRequestDTO;
import com.utp.api.application.response.dto.UserResponseDTO;
import com.utp.api.application.service.impl.UserServiceImpl;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.infraestructure.util.IMediatorService;
import com.utp.api.shared.util.IJwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private IMediatorService mediatorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IJwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        // Simular que no existe usuario con ese nombre de usuario
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class))).thenReturn(Optional.empty());

        // Simular codificación de la contraseña
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // Simular la creación de un nuevo usuario
        UserDomain userDomain = new UserDomain(1L, "testUser", "test@example.com", "hashedPassword");
        when(mediatorService.dispatch(any(CreateUserCommand.class))).thenReturn(userDomain);

        // Crear un DTO de registro
        UserRegisterRequestDTO userRegisterDTO = new UserRegisterRequestDTO("testUser", "test@example.com", "Password123");

        // Llamar al método de servicio
        UserResponseDTO response = userServiceImpl.registerUser(userRegisterDTO);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals("testUser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
        verify(mediatorService, times(1)).dispatch(any(CreateUserCommand.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Simular que el usuario ya existe
        UserDomain existingUser = new UserDomain(1L, "testUser", "test@example.com", "hashedPassword");
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class))).thenReturn(Optional.of(existingUser));

        // Crear un DTO de registro
        UserRegisterRequestDTO userRegisterDTO = new UserRegisterRequestDTO("testUser", "test@example.com", "Password123");

        // Verificar que se arroje una excepción cuando el usuario ya existe
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.registerUser(userRegisterDTO);
        });

        assertEquals("El usuario ya existe.", exception.getMessage());
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
        verify(mediatorService, times(0)).dispatch(any(CreateUserCommand.class));
    }

    @Test
    public void testLoginUser_Success() {
        // Simular la búsqueda del usuario
        UserDomain userDomain = new UserDomain(1L, "testUser", "test@example.com", "hashedPassword");
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class))).thenReturn(Optional.of(userDomain));

        // Simular la coincidencia de la contraseña
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Simular la creación del token JWT
        when(jwtTokenProvider.createToken(anyString())).thenReturn("jwtToken");

        // Crear un DTO de login
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("testUser", "Password123");

        // Llamar al método de servicio
        String token = userServiceImpl.loginUser(loginRequestDTO);
   
        assertNotNull(token);
        assertEquals("jwtToken", token);
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(jwtTokenProvider, times(1)).createToken(anyString());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        // Simular que el usuario no fue encontrado
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class))).thenReturn(Optional.empty());

        // Crear un DTO de login
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("nonExistentUser", "Password123");

        // Verificar que se arroje una excepción cuando el usuario no es encontrado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.loginUser(loginRequestDTO);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
        verify(passwordEncoder, times(0)).matches(anyString(), anyString());
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        // Simular la búsqueda del usuario
        UserDomain userDomain = new UserDomain(1L, "testUser", "test@example.com", "hashedPassword");
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class))).thenReturn(Optional.of(userDomain));

        // Simular que la contraseña no coincide
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Crear un DTO de login
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("testUser", "WrongPassword");

        // Verificar que se arroje una excepción cuando la contraseña es incorrecta
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userServiceImpl.loginUser(loginRequestDTO);
        });

        assertEquals("Contraseña incorrecta", exception.getMessage());
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(jwtTokenProvider, times(0)).createToken(anyString());
    }
}