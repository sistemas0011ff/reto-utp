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
        String title = "Título de prueba";
        String content = "Contenido de prueba";
        LocalDateTime createdAt = LocalDateTime.now();
        UserEntity user = new UserEntity(); // Simular un objeto UserEntity

        // Crear instancia de NoteEntity
        NoteEntity note = new NoteEntity(title, content, createdAt, user);

        // Verificar que los datos se asignaron correctamente
        assertEquals(title, note.getTitle(), "El título no es el esperado");
        assertEquals(content, note.getContent(), "El contenido no es el esperado");
        assertEquals(createdAt, note.getCreatedAt(), "La fecha de creación no es la esperada");
        assertEquals(user, note.getUser(), "El usuario no es el esperado");
    }

    @Test
    public void testSettersAndGetters() {
        // Crear instancia de NoteEntity
        NoteEntity note = new NoteEntity();

        // Datos de prueba
        String newTitle = "Nuevo título";
        String newContent = "Nuevo contenido";
        LocalDateTime newCreatedAt = LocalDateTime.now();
        UserEntity newUser = new UserEntity(); // Simular un nuevo objeto UserEntity

        // Usar setters
        note.setTitle(newTitle);
        note.setContent(newContent);
        note.setCreatedAt(newCreatedAt);
        note.setUser(newUser);

        // Verificar los getters
        assertEquals(newTitle, note.getTitle(), "El título no es el esperado después de usar el setter");
        assertEquals(newContent, note.getContent(), "El contenido no es el esperado después de usar el setter");
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