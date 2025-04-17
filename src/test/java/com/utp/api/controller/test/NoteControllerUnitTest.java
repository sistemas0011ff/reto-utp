package com.utp.api.controller.test;

import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.NoteResponseDTO;
import com.utp.api.application.service.NoteService;
import com.utp.api.controller.NoteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NoteControllerUnitTest {

    @Mock
    private NoteService noteService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configurar SecurityContextHolder mock
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testUser");
    }

    @Test
    public void testCreateNote_Success() {
        // DTO para crear nota con calificación numérica
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setScore(17.5);
        noteRequest.setDescription("Examen parcial de física");

        // Simular comportamiento del servicio
        NoteResponseDTO createdNote = new NoteResponseDTO(1L, 17.5, "Examen parcial de física", null, "testUser");
        when(noteService.createNote(any(NoteCreateRequestDTO.class), anyString())).thenReturn(createdNote);

        // Llamar al método del controlador
        ResponseEntity<NoteResponseDTO> result = noteController.createNote(noteRequest);

        // Verificar el resultado
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(17.5, result.getBody().getScore());
        assertEquals("Examen parcial de física", result.getBody().getDescription());
    }

    @Test
    public void testCreateNote_InvalidScore() {
        // DTO con calificación inválida (fuera de rango)
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setScore(21.5);  // Esto debería desencadenar una validación fallida (mayor a 20)
        noteRequest.setDescription("Descripción válida");

        // Simular comportamiento del servicio (arroja una excepción)
        when(noteService.createNote(any(NoteCreateRequestDTO.class), anyString()))
            .thenThrow(new IllegalArgumentException("Error de validación"));

        // Llamar al método del controlador
        // Nota: En la implementación real, este error sería capturado por un ExceptionHandler
        try {
            noteController.createNote(noteRequest);
        } catch (IllegalArgumentException e) {
            assertEquals("Error de validación", e.getMessage());
        }
    }

    @Test
    public void testListNotes_Success() {
        // Simular comportamiento del servicio
        List<NoteResponseDTO> notes = new ArrayList<>();
        notes.add(new NoteResponseDTO(1L, 18.0, "Trabajo final de programación", null, "testUser"));
        when(noteService.listNotesByUsername("testUser")).thenReturn(notes);

        // Llamar al método del controlador
        ResponseEntity<List<NoteResponseDTO>> result = noteController.listNotes();

        // Verificar el resultado
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        assertEquals(18.0, result.getBody().get(0).getScore());
        assertEquals("Trabajo final de programación", result.getBody().get(0).getDescription());
    }
}