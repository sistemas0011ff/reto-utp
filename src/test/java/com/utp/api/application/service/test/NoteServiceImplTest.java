package com.utp.api.application.service.test;

import com.utp.api.application.command.CreateNoteCommand;
import com.utp.api.application.query.FindNotesByUsernameQuery;
import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.NoteResponseDTO;
import com.utp.api.application.service.impl.NoteServiceImpl;
import com.utp.api.domain.model.NoteDomain;
import com.utp.api.infraestructure.util.IMediatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoteServiceImplTest {

    @Mock
    private IMediatorService mediatorService;

    @InjectMocks
    private NoteServiceImpl noteServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNote_Success() {
        // Simular el comando para crear una nota
        NoteDomain noteDomain = new NoteDomain(1L, "Test Title", "Test Content", LocalDateTime.now(), "testUser");
        when(mediatorService.dispatch(any(CreateNoteCommand.class))).thenReturn(noteDomain);

        // Crear el DTO de solicitud
        NoteCreateRequestDTO requestDTO = new NoteCreateRequestDTO();
        requestDTO.setTitle("Test Title");
        requestDTO.setContent("Test Content");

        // Llamar al método de servicio
        NoteResponseDTO responseDTO = noteServiceImpl.createNote(requestDTO, "testUser");

        // Verificar el resultado
        assertNotNull(responseDTO);
        assertEquals("Test Title", responseDTO.getTitle());
        assertEquals("Test Content", responseDTO.getContent());
        assertEquals("testUser", responseDTO.getUsername());
        verify(mediatorService, times(1)).dispatch(any(CreateNoteCommand.class));
    }

    @Test
    public void testListNotesByUsername_Success() {
        // Simular la respuesta de búsqueda de notas
        List<NoteDomain> noteDomainList = new ArrayList<>();
        noteDomainList.add(new NoteDomain(1L, "Test Title", "Test Content", LocalDateTime.now(), "testUser"));
        when(mediatorService.dispatch(any(FindNotesByUsernameQuery.class))).thenReturn(noteDomainList);

        // Llamar al método de servicio
        List<NoteResponseDTO> responseList = noteServiceImpl.listNotesByUsername("testUser");

        // Verificar el resultado
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("Test Title", responseList.get(0).getTitle());
        assertEquals("Test Content", responseList.get(0).getContent());
        assertEquals("testUser", responseList.get(0).getUsername());
        verify(mediatorService, times(1)).dispatch(any(FindNotesByUsernameQuery.class));
    }

    @Test
    public void testListNotesByUsername_EmptyList() {
        // Simular una lista vacía de notas
        when(mediatorService.dispatch(any(FindNotesByUsernameQuery.class))).thenReturn(new ArrayList<>());

        // Llamar al método de servicio
        List<NoteResponseDTO> responseList = noteServiceImpl.listNotesByUsername("testUser");

        // Verificar el resultado
        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());
        verify(mediatorService, times(1)).dispatch(any(FindNotesByUsernameQuery.class));
    }
}