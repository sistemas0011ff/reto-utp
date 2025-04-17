package com.utp.api.application.command.test;

import org.junit.jupiter.api.Test;
import com.utp.api.application.command.CreateNoteCommand;
import static org.junit.jupiter.api.Assertions.*;

public class CreateNoteCommandTest {
    
    @Test
    public void testCreateNoteCommand_Success() {
        // Crear una instancia del comando con valores numéricos
        Double testScore = 17.5;
        String description = "Examen parcial";
        CreateNoteCommand command = new CreateNoteCommand(testScore, description, "testUser");
        
        // Verificar que los valores se inicializan correctamente
        assertEquals(testScore, command.getScore());
        assertEquals(description, command.getDescription());
        assertEquals("testUser", command.getUsername());
    }
    
    @Test
    public void testGetCommandType() {
        // Crear una instancia del comando
        CreateNoteCommand command = new CreateNoteCommand(15.0, "Test Description", "testUser");
        
        // Verificar que el tipo de comando es el correcto
        assertEquals(CreateNoteCommand.class, command.getCommandType());
    }
    
    @Test
    public void testCreateNoteCommand_NullValues() {
        // Crear una instancia del comando con valores nulos
        CreateNoteCommand command = new CreateNoteCommand(null, null, null);
        
        // Verificar que los valores nulos se manejan correctamente
        assertNull(command.getScore());
        assertNull(command.getDescription());
        assertNull(command.getUsername());
    }
}