package com.utp.api.application.command.test;

import org.junit.jupiter.api.Test;

import com.utp.api.application.command.CreateNoteCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CreateNoteCommandTest {

    @Test
    public void testCreateNoteCommand_Success() {
        // Crear una instancia del comando
        CreateNoteCommand command = new CreateNoteCommand("Test Title", "Test Content", "testUser");

        // Verificar que los valores se inicializan correctamente
        assertEquals("Test Title", command.getTitle());
        assertEquals("Test Content", command.getContent());
        assertEquals("testUser", command.getUsername());
    }

    @Test
    public void testGetCommandType() {
        // Crear una instancia del comando
        CreateNoteCommand command = new CreateNoteCommand("Test Title", "Test Content", "testUser");

        // Verificar que el tipo de comando es el correcto
        assertEquals(CreateNoteCommand.class, command.getCommandType());
    }

    @Test
    public void testCreateNoteCommand_NullValues() {
        // Crear una instancia del comando con valores nulos
        CreateNoteCommand command = new CreateNoteCommand(null, null, null);

        // Verificar que los valores nulos se manejan correctamente
        assertNull(command.getTitle());
        assertNull(command.getContent());
        assertNull(command.getUsername());
    }
}