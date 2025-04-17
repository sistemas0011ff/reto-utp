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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

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
        // DTO para crear nota con calificación numérica
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setScore(18.5);
        noteRequest.setDescription("Examen final de matemáticas");

        // Simular comportamiento del servicio
        NoteResponseDTO response = new NoteResponseDTO(1L, 18.5, "Examen final de matemáticas", null, "username");
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
    public void testCreateNote_ScoreOutOfRange() throws Exception {
        // DTO con calificación fuera de rango
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setScore(25.0); // Fuera del rango válido (0-20)
        noteRequest.setDescription("Descripción válida");

        // Realizar el test con usuario autenticado
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest))
                        .with(user("username"))) // Simula usuario autenticado
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error de validación"));
    }

    @Test
    public void testCreateNote_NegativeScore() throws Exception {
        // DTO con calificación negativa
        NoteCreateRequestDTO noteRequest = new NoteCreateRequestDTO();
        noteRequest.setScore(-5.0); // Calificación negativa no permitida
        noteRequest.setDescription("Descripción válida");

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
        NoteResponseDTO response = new NoteResponseDTO(1L, 17.0, "Examen parcial", null, "username");
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