package com.utp.api.controller.test;

import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.ApiResponseDTO;
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
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class NoteControllerUnitTest {

    @Mock
    private NoteService noteService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNote_Success() {
        // Simular el UserDetails con un nombre de usuario
        when(userDetails.getUsername()).thenReturn("testUser");

        // DTO para crear nota
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setTitle("Nota de ejemplo");
        noteRequest.setContent("Contenido de la nota");

        // Simular comportamiento del servicio
        NoteResponseDTO createdNote = new NoteResponseDTO(1L, "Nota de ejemplo", "Contenido de la nota", null, "testUser");
        when(noteService.createNote(any(NoteCreateRequestDTO.class), anyString())).thenReturn(createdNote);

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<NoteResponseDTO>> result = noteController.createNote(noteRequest, userDetails);

        // Verificar el resultado
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Nota creada exitosamente", result.getBody().getMessage());
        assertEquals("Nota de ejemplo", result.getBody().getData().getTitle());
        assertEquals("Contenido de la nota", result.getBody().getData().getContent());
    }

    @Test
    public void testCreateNote_InvalidTitle() {
        // Simular el UserDetails con un nombre de usuario
        when(userDetails.getUsername()).thenReturn("testUser");

        // DTO con título inválido
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setTitle("string");  // Esto debería desencadenar una validación fallida
        noteRequest.setContent("Contenido válido");

        // Simular comportamiento del servicio (arroja una excepción)
        when(noteService.createNote(any(NoteCreateRequestDTO.class), anyString())).thenThrow(new IllegalArgumentException("Error de validación"));

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<NoteResponseDTO>> result = noteController.createNote(noteRequest, userDetails);

        // Verificar el resultado
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Error de validación", result.getBody().getMessage());
    }

    @Test
    public void testListNotes_Success() {
        // Simular el UserDetails con un nombre de usuario
        when(userDetails.getUsername()).thenReturn("testUser");

        // Simular comportamiento del servicio
        List<NoteResponseDTO> notes = new ArrayList<>();
        notes.add(new NoteResponseDTO(1L, "Nota de ejemplo", "Contenido de la nota", null, "testUser"));
        when(noteService.listNotesByUsername("testUser")).thenReturn(notes);

        // Llamar al método del controlador
        ResponseEntity<ApiResponseDTO<List<NoteResponseDTO>>> result = noteController.listNotes(userDetails);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Notas obtenidas exitosamente", result.getBody().getMessage());
        assertEquals(1, result.getBody().getData().size());
        assertEquals("Nota de ejemplo", result.getBody().getData().get(0).getTitle());
    }
}
