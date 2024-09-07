package com.utp.api.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.NoteResponseDTO;
import com.utp.api.application.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user; // <- Import necesario

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateNote_Success() throws Exception {
        // DTO para crear nota
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setTitle("Nota de ejemplo");
        noteRequest.setContent("Contenido de la nota");

        // Simular comportamiento del servicio
        NoteResponseDTO response = new NoteResponseDTO(1L, "Nota de ejemplo", "Contenido de la nota", null, "username");
        when(noteService.createNote(any(NoteCreateRequestDTO.class), any(String.class))).thenReturn(response);

        // Realizar el test con usuario autenticado
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest))
                        .with(user("username"))) // Simula usuario autenticado
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Nota creada exitosamente"));
    }

    @Test
    public void testCreateNote_TitleIsString() throws Exception {
        // DTO con "string" como título
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setTitle("string");
        noteRequest.setContent("Contenido válido");

        // Realizar el test con usuario autenticado
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest))
                        .with(user("username"))) // Simula usuario autenticado
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"));
    }

    @Test
    public void testCreateNote_ContentTooShort() throws Exception {
        // DTO con contenido demasiado corto
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setTitle("Título válido");
        noteRequest.setContent("a");

        // Realizar el test con usuario autenticado
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest))
                        .with(user("username"))) // Simula usuario autenticado
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"));
    }

    @Test
    public void testListNotes_Success() throws Exception {
        // Simular comportamiento del servicio
        NoteResponseDTO response = new NoteResponseDTO(1L, "Nota de ejemplo", "Contenido de la nota", null, "username");
        when(noteService.listNotesByUsername("username")).thenReturn(Collections.singletonList(response));

        // Realizar el test con usuario autenticado
        mockMvc.perform(get("/api/notes")
                        .with(user("username"))) // Simula usuario autenticado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Notas obtenidas exitosamente"));
    }
    
    @Test
    public void testListNotes_Unauthenticated() throws Exception {
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isUnauthorized());
    }
}
