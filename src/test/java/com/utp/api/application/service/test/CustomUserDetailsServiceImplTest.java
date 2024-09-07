package com.utp.api.application.service.test;

import com.utp.api.application.query.FindUserByUsernameQuery;
import com.utp.api.application.service.impl.CustomUserDetailsServiceImpl;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.infraestructure.util.IMediatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceImplTest {

    private CustomUserDetailsServiceImpl userDetailsService;
    private IMediatorService mediatorService;

    @BeforeEach
    public void setUp() {
        mediatorService = mock(IMediatorService.class);
        userDetailsService = new CustomUserDetailsServiceImpl(mediatorService);
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        // Simular un usuario en la base de datos
        UserDomain mockUser = new UserDomain(1L, "validUsername", "validEmail@example.com", "validPassword");
        
        // Configurar el mediador para devolver el usuario simulado
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class)))
                .thenReturn(Optional.of(mockUser));

        // Ejecutar el método y verificar
        UserDetails userDetails = userDetailsService.loadUserByUsername("validUsername");

        // Verificaciones
        assertNotNull(userDetails);
        assertEquals("validUsername", userDetails.getUsername());
        assertEquals("validPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));

        // Verificar que el mediator haya sido invocado
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
    }


    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Simular que el usuario no se encuentra
        when(mediatorService.dispatch(any(FindUserByUsernameQuery.class)))
                .thenReturn(Optional.empty());

        // Ejecutar el método y verificar que se lance una excepción
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("invalidUsername");
        });

        // Verificar que el mediator haya sido invocado
        verify(mediatorService, times(1)).dispatch(any(FindUserByUsernameQuery.class));
    }
}