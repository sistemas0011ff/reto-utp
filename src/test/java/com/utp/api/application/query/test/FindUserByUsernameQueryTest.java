package com.utp.api.application.query.test;


import org.junit.jupiter.api.Test;

import com.utp.api.application.query.FindUserByUsernameQuery;

import static org.junit.jupiter.api.Assertions.*;

public class FindUserByUsernameQueryTest {

    @Test
    public void testGetUsername() {
        // Crear una instancia de FindUserByUsernameQuery con un nombre de usuario de prueba
        String username = "testUser";
        FindUserByUsernameQuery query = new FindUserByUsernameQuery(username);

        // Verificar que el nombre de usuario es el esperado
        assertEquals(username, query.getUsername(), "El nombre de usuario no coincide");
    }

    @Test
    public void testUsernameIsNotNull() {
        // Crear una instancia de FindUserByUsernameQuery con un nombre de usuario de prueba
        String username = "anotherUser";
        FindUserByUsernameQuery query = new FindUserByUsernameQuery(username);

        // Verificar que el nombre de usuario no es nulo
        assertNotNull(query.getUsername(), "El nombre de usuario no debe ser nulo");
    }
}