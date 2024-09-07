package com.utp.api.application.command.test;

import com.utp.api.application.command.CreateUserCommand;
import com.utp.api.application.command.CreateUserCommandHandler;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserCommandHandlerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserCommandHandler createUserCommandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle_Success() {
        // Datos de entrada para el comando
        CreateUserCommand command = new CreateUserCommand("testUser", "test@example.com", "password123");

        // Crear un dominio de usuario simulado que será devuelto por el repositorio
        UserDomain savedUser = new UserDomain(1L, "testUser", "test@example.com", "password123");

        // Simular el comportamiento del repositorio
        when(userRepository.save(any(UserDomain.class))).thenReturn(savedUser);

        // Ejecutar el método handle
        UserDomain result = createUserCommandHandler.handle(command);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());

        // Verificar que el método save del repositorio fue llamado
        verify(userRepository, times(1)).save(any(UserDomain.class));
    }

    @Test
    public void testHandle_RepositorySaveFailure() {
        // Datos de entrada para el comando
        CreateUserCommand command = new CreateUserCommand("testUser", "test@example.com", "password123");

        // Simular una excepción al intentar guardar en el repositorio
        when(userRepository.save(any(UserDomain.class))).thenThrow(new RuntimeException("Error al guardar en el repositorio"));

        // Verificar que se lanza la excepción correcta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createUserCommandHandler.handle(command);
        });

        assertEquals("Error al guardar en el repositorio", exception.getMessage());

        // Verificar que el método save del repositorio fue llamado
        verify(userRepository, times(1)).save(any(UserDomain.class));
    }

    @Test
    public void testGetCommandType() {
        // Verificar que el tipo de comando es el correcto
        assertEquals(CreateUserCommand.class, createUserCommandHandler.getCommandType());
    }
}