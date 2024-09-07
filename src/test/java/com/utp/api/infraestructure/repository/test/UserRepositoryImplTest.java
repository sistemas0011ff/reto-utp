package com.utp.api.infraestructure.repository.test;

import com.utp.api.domain.model.UserDomain;
import com.utp.api.infraestructure.repository.JpaUserRepository;
import com.utp.api.infraestructure.repository.UserEntity;
import com.utp.api.infraestructure.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserRepositoryImplTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private UserEntity userEntity;
    private UserDomain userDomain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configurar un UserEntity y un UserDomain de ejemplo para las pruebas
        userEntity = new UserEntity(1L, "testUser", "test@example.com", "password123");
        userDomain = new UserDomain(1L, "testUser", "test@example.com", "password123");
    }

    @Test
    public void testFindByUsername_Success() {
        // Configurar el mock para devolver el UserEntity cuando se llame a findByUsername
        when(jpaUserRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        // Llamar al método findByUsername del UserRepositoryImpl
        Optional<UserDomain> result = userRepository.findByUsername("testUser");

        // Verificar que el resultado no sea vacío y que los datos sean correctos
        assertTrue(result.isPresent());
        assertEquals(userDomain.getUsername(), result.get().getUsername());
        assertEquals(userDomain.getEmail(), result.get().getEmail());
        assertEquals(userDomain.getPassword(), result.get().getPassword());

        // Verificar que el mock de JpaUserRepository fue llamado una vez
        verify(jpaUserRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testFindByUsername_NotFound() {
        // Configurar el mock para devolver un Optional vacío
        when(jpaUserRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Llamar al método findByUsername con un nombre de usuario no existente
        Optional<UserDomain> result = userRepository.findByUsername("unknownUser");

        // Verificar que el resultado esté vacío
        assertFalse(result.isPresent());

        // Verificar que el mock de JpaUserRepository fue llamado una vez
        verify(jpaUserRepository, times(1)).findByUsername("unknownUser");
    }

    @Test
    public void testSaveUser_Success() {
        // Configurar el mock para devolver el UserEntity cuando se guarde un usuario
        when(jpaUserRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Llamar al método save del UserRepositoryImpl
        UserDomain savedUser = userRepository.save(userDomain);

        // Verificar que los datos del usuario guardado sean correctos
        assertEquals(userDomain.getUsername(), savedUser.getUsername());
        assertEquals(userDomain.getEmail(), savedUser.getEmail());
        assertEquals(userDomain.getPassword(), savedUser.getPassword());

        // Verificar que el mock de JpaUserRepository fue llamado una vez
        verify(jpaUserRepository, times(1)).save(any(UserEntity.class));
    }
}