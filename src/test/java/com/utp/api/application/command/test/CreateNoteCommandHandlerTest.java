package com.utp.api.application.command.test;

import com.utp.api.application.command.CreateNoteCommand;
import com.utp.api.application.command.CreateNoteCommandHandler;
import com.utp.api.domain.model.NoteDomain;
import com.utp.api.domain.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateNoteCommandHandlerTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private CreateNoteCommandHandler createNoteCommandHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandle_Success() {
        // Configurar el comando de prueba
        CreateNoteCommand command = new CreateNoteCommand("Test Title", "Test Content", "testUser");

        // Configurar la respuesta esperada del repositorio
        NoteDomain savedNote = new NoteDomain(1L, "Test Title", "Test Content", LocalDateTime.now(), "testUser");
        when(noteRepository.save(any(NoteDomain.class))).thenReturn(savedNote);

        // Ejecutar el método handle
        NoteDomain result = createNoteCommandHandler.handle(command);

        // Verificar que el repositorio fue llamado con el objeto correcto
        ArgumentCaptor<NoteDomain> noteCaptor = ArgumentCaptor.forClass(NoteDomain.class);
        verify(noteRepository).save(noteCaptor.capture());

        // Verificar que los valores son correctos
        NoteDomain capturedNote = noteCaptor.getValue();
        assertEquals("Test Title", capturedNote.getTitle());
        assertEquals("Test Content", capturedNote.getContent());
        assertEquals("testUser", capturedNote.getUsername());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void testHandle_NullCommand() {
        // Verificar que lanzar una excepción si el comando es nulo
        assertThrows(NullPointerException.class, () -> createNoteCommandHandler.handle(null));
    }
}