package com.utp.api.infraestructure.repository.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.utp.api.infraestructure.repository.NoteEntity;
import com.utp.api.infraestructure.repository.UserEntity;
import java.time.LocalDateTime;

public class NoteEntityTest {
    
    @Test
    public void testNoteEntityCreation() {
        // Datos de prueba
        Double score = 17.5;
        String description = "Examen parcial de química";
        LocalDateTime createdAt = LocalDateTime.now();
        UserEntity user = new UserEntity(); // Simular un objeto UserEntity
        
        // Crear instancia de NoteEntity
        NoteEntity note = new NoteEntity(score, description, createdAt, user);
        
        // Verificar que los datos se asignaron correctamente
        assertEquals(score, note.getScore(), "La calificación no es la esperada");
        assertEquals(description, note.getDescription(), "La descripción no es la esperada");
        assertEquals(createdAt, note.getCreatedAt(), "La fecha de creación no es la esperada");
        assertEquals(user, note.getUser(), "El usuario no es el esperado");
    }
    
    @Test
    public void testSettersAndGetters() {
        // Crear instancia de NoteEntity
        NoteEntity note = new NoteEntity();
        
        // Datos de prueba
        Double newScore = 19.0;
        String newDescription = "Trabajo final de programación";
        LocalDateTime newCreatedAt = LocalDateTime.now();
        UserEntity newUser = new UserEntity(); // Simular un nuevo objeto UserEntity
        
        // Usar setters
        note.setScore(newScore);
        note.setDescription(newDescription);
        note.setCreatedAt(newCreatedAt);
        note.setUser(newUser);
        
        // Verificar los getters
        assertEquals(newScore, note.getScore(), "La calificación no es la esperada después de usar el setter");
        assertEquals(newDescription, note.getDescription(), "La descripción no es la esperada después de usar el setter");
        assertEquals(newCreatedAt, note.getCreatedAt(), "La fecha de creación no es la esperada después de usar el setter");
        assertEquals(newUser, note.getUser(), "El usuario no es el esperado después de usar el setter");
    }
    
    @Test
    public void testIdSetterAndGetter() {
        // Crear instancia de NoteEntity
        NoteEntity note = new NoteEntity();
        
        // Establecer y obtener el ID
        Long id = 1L;
        note.setId(id);
        
        // Verificar que el ID se estableció correctamente
        assertEquals(id, note.getId(), "El ID no es el esperado");
    }
}