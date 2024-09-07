package com.utp.api.infraestructure.repository.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.utp.api.infraestructure.repository.NoteEntity;
import com.utp.api.infraestructure.repository.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    private UserEntity userEntity;
    private NoteEntity noteEntity;

    @BeforeEach
    public void setUp() {
        // Crear un UserEntity para usar en las pruebas
        userEntity = new UserEntity(1L, "testUser", "test@example.com", "password123");

        // Crear una NoteEntity para agregarla a la lista de notas del usuario
        noteEntity = new NoteEntity();
        noteEntity.setId(1L);
        noteEntity.setTitle("Test Note");
        noteEntity.setContent("This is a test note.");
    }

    @Test
    public void testGettersAndSetters() {
        // Verificar que los getters devuelvan los valores correctos
        assertEquals(1L, userEntity.getId());
        assertEquals("testUser", userEntity.getUsername());
        assertEquals("test@example.com", userEntity.getEmail());
        assertEquals("password123", userEntity.getPassword());

        // Modificar los valores y verificar los setters
        userEntity.setUsername("newUser");
        assertEquals("newUser", userEntity.getUsername());

        userEntity.setEmail("new@example.com");
        assertEquals("new@example.com", userEntity.getEmail());

        userEntity.setPassword("newPassword123");
        assertEquals("newPassword123", userEntity.getPassword());
    }

    @Test
    public void testNotesRelationship() {
        // Crear una lista de notas
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(noteEntity);

        // Asignar las notas al usuario
        userEntity.setNotes(notes);

        // Verificar que la relación de notas se estableció correctamente
        assertEquals(1, userEntity.getNotes().size());
        assertEquals("Test Note", userEntity.getNotes().get(0).getTitle());

        // Modificar la lista de notas
        NoteEntity anotherNote = new NoteEntity();
        anotherNote.setId(2L);
        anotherNote.setTitle("Another Note");
        anotherNote.setContent("This is another test note.");

        notes.add(anotherNote);
        userEntity.setNotes(notes);

        // Verificar que la lista de notas se actualizó correctamente
        assertEquals(2, userEntity.getNotes().size());
        assertEquals("Another Note", userEntity.getNotes().get(1).getTitle());
    }

    @Test
    public void testDefaultConstructor() {
        // Crear un UserEntity utilizando el constructor por defecto
        UserEntity defaultUserEntity = new UserEntity();

        // Verificar que los valores iniciales son null
        assertNull(defaultUserEntity.getId());
        assertNull(defaultUserEntity.getUsername());
        assertNull(defaultUserEntity.getEmail());
        assertNull(defaultUserEntity.getPassword());
        assertNull(defaultUserEntity.getNotes());
    }
}