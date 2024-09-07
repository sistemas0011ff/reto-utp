package com.utp.api.application.query.test;

import com.utp.api.application.query.FindNotesByUsernameQuery;
import com.utp.api.application.query.FindNotesByUsernameQueryHandler;
import com.utp.api.domain.model.NoteDomain;
import com.utp.api.domain.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FindNotesByUsernameQueryHandlerTest {

    private NoteRepository noteRepository;
    private FindNotesByUsernameQueryHandler queryHandler;

    @BeforeEach
    public void setUp() {
        noteRepository = Mockito.mock(NoteRepository.class);
        queryHandler = new FindNotesByUsernameQueryHandler(noteRepository);
    }

    @Test
    public void testHandle_ReturnsNotes() {
        // Datos de prueba
        String username = "testUser";
        NoteDomain note1 = new NoteDomain(1L, "Note 1", "Content 1", LocalDateTime.now(), username);
        NoteDomain note2 = new NoteDomain(2L, "Note 2", "Content 2", LocalDateTime.now(), username);

        // Simulación del comportamiento del repositorio
        when(noteRepository.findByUsername(username)).thenReturn(Arrays.asList(note1, note2));

        // Ejecutar el método handle
        FindNotesByUsernameQuery query = new FindNotesByUsernameQuery(username);
        List<NoteDomain> result = queryHandler.handle(query);

        // Verificar los resultados
        assertEquals(2, result.size());
        assertEquals(note1.getId(), result.get(0).getId());
        assertEquals(note2.getId(), result.get(1).getId());
    }

    @Test
    public void testHandle_ReturnsEmptyList() {
        // Datos de prueba
        String username = "testUser";

        // Simulación del comportamiento del repositorio (sin notas para el usuario)
        when(noteRepository.findByUsername(username)).thenReturn(Arrays.asList());

        // Ejecutar el método handle
        FindNotesByUsernameQuery query = new FindNotesByUsernameQuery(username);
        List<NoteDomain> result = queryHandler.handle(query);

        // Verificar que la lista está vacía
        assertEquals(0, result.size());
    }
}