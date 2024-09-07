package com.utp.api.application.command.test;


import org.junit.jupiter.api.Test;

import com.utp.api.application.command.CreateUserCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserCommandTest {

    @Test
    public void testCreateUserCommand_Success() {
        // Crear un comando con datos de usuario
        String username = "testUser";
        String email = "test@example.com";
        String password = "password123";

        // Crear el comando
        CreateUserCommand command = new CreateUserCommand(username, email, password);

        // Verificar que los valores se asignaron correctamente
        assertEquals("testUser", command.getUsername());
        assertEquals("test@example.com", command.getEmail());
        assertEquals("password123", command.getPassword());
    }

    @Test
    public void testGetCommandType() {
        // Crear un comando con datos de usuario
        CreateUserCommand command = new CreateUserCommand("testUser", "test@example.com", "password123");

        // Verificar el tipo de comando
        assertEquals(CreateUserCommand.class, command.getCommandType());
    }

    @Test
    public void testCreateUserCommand_NullValues() {
        // Prueba para verificar que el comando puede aceptar valores nulos (si es permitido)
        CreateUserCommand command = new CreateUserCommand(null, null, null);

        assertNull(command.getUsername());
        assertNull(command.getEmail());
        assertNull(command.getPassword());
    }
}