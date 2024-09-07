package com.utp.api.application.query.test;

import com.utp.api.application.query.FindUserByUsernameQuery;
import com.utp.api.application.query.FindUserByUsernameQueryHandler;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class FindUserByUsernameQueryHandlerTest {

    private UserRepository userRepository;
    private FindUserByUsernameQueryHandler queryHandler;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        queryHandler = new FindUserByUsernameQueryHandler(userRepository);
    }

    @Test
    public void testHandle_UserFound() {
        // Datos de prueba
        String username = "testUser";
        UserDomain user = new UserDomain(1L, username, "test@example.com", "hashedPassword");

        // Simulación del comportamiento del repositorio
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Ejecutar el método handle
        FindUserByUsernameQuery query = new FindUserByUsernameQuery(username);
        Optional<UserDomain> result = queryHandler.handle(query);

        // Verificar que se encontró el usuario
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getUsername(), result.get().getUsername());
    }

    @Test
    public void testHandle_UserNotFound() {
        // Datos de prueba
        String username = "nonExistingUser";

        // Simulación del comportamiento del repositorio (sin usuario encontrado)
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Ejecutar el método handle
        FindUserByUsernameQuery query = new FindUserByUsernameQuery(username);
        Optional<UserDomain> result = queryHandler.handle(query);

        // Verificar que no se encontró el usuario
        assertTrue(result.isEmpty());
    }
}