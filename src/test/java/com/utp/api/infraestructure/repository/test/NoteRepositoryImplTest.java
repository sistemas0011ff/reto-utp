package com.utp.api.infraestructure.repository.test;

import com.utp.api.domain.model.NoteDomain;
import com.utp.api.infraestructure.repository.JpaNoteRepository;
import com.utp.api.infraestructure.repository.JpaUserRepository;
import com.utp.api.infraestructure.repository.NoteEntity;
import com.utp.api.infraestructure.repository.NoteRepositoryImpl;
import com.utp.api.infraestructure.repository.UserEntity;
import com.utp.api.infraestructure.util.NoteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class NoteRepositoryImplTest {

    @Mock
    private JpaNoteRepository jpaNoteRepository;

    @Mock
    private JpaUserRepository jpaUserRepository;

    @InjectMocks
    private NoteRepositoryImpl noteRepositoryImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUsername_Success() {
        // Datos de prueba
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        NoteEntity noteEntity = new NoteEntity("Test Title", "Test Content", LocalDateTime.now(), userEntity);
        when(jpaNoteRepository.findByUser_Username("testUser")).thenReturn(Collections.singletonList(noteEntity));

        // Llamada al método
        List<NoteDomain> result = noteRepositoryImpl.findByUsername("testUser");

        // Verificaciones
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        assertEquals("testUser", result.get(0).getUsername());

        verify(jpaNoteRepository, times(1)).findByUser_Username("testUser");
    }

    @Test
    public void testSave_Success() {
        // Datos de prueba
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        NoteDomain noteDomain = new NoteDomain(null, "Test Title", "Test Content", LocalDateTime.now(), "testUser");
        NoteEntity noteEntity = NoteMapper.toPersistence(noteDomain, userEntity);

        when(jpaUserRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
        when(jpaNoteRepository.save(any(NoteEntity.class))).thenReturn(noteEntity);

        // Llamada al método
        NoteDomain result = noteRepositoryImpl.save(noteDomain);

        // Verificaciones
        assertEquals("Test Title", result.getTitle());
        assertEquals("testUser", result.getUsername());

        verify(jpaUserRepository, times(1)).findByUsername("testUser");
        verify(jpaNoteRepository, times(1)).save(any(NoteEntity.class));
    }

    @Test
    public void testSave_UserNotFound() {
        // Datos de prueba
        NoteDomain noteDomain = new NoteDomain(null, "Test Title", "Test Content", LocalDateTime.now(), "unknownUser");

        when(jpaUserRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Verificación de excepción
        assertThrows(IllegalArgumentException.class, () -> noteRepositoryImpl.save(noteDomain), "Usuario no encontrado");

        verify(jpaUserRepository, times(1)).findByUsername("unknownUser");
        verify(jpaNoteRepository, times(0)).save(any(NoteEntity.class));
    }
}