package com.utp.api.application.query.test;

import org.junit.jupiter.api.Test;

import com.utp.api.application.query.FindNotesByUsernameQuery;

import static org.junit.jupiter.api.Assertions.*;

public class FindNotesByUsernameQueryTest {

    @Test
    public void testGetUsername() {
        // Crear una instancia de la consulta con un nombre de usuario
        String expectedUsername = "testUser";
        FindNotesByUsernameQuery query = new FindNotesByUsernameQuery(expectedUsername);

        // Verificar que el nombre de usuario devuelto es el correcto
        assertEquals(expectedUsername, query.getUsername());
    }

    @Test
    public void testNullUsername() {
        // Crear una consulta con un valor nulo para el nombre de usuario
        FindNotesByUsernameQuery query = new FindNotesByUsernameQuery(null);

        // Verificar que el nombre de usuario es nulo
        assertNull(query.getUsername());
    }

    @Test
    public void testEmptyUsername() {
        // Crear una consulta con un nombre de usuario vacío
        String expectedUsername = "";
        FindNotesByUsernameQuery query = new FindNotesByUsernameQuery(expectedUsername);

        // Verificar que el nombre de usuario devuelto es una cadena vacía
        assertEquals(expectedUsername, query.getUsername());
    }
}