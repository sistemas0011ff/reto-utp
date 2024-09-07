package com.utp.api.application.service.impl;

import com.utp.api.application.query.FindUserByUsernameQuery;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.infraestructure.util.IMediatorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final IMediatorService mediatorService;

    public CustomUserDetailsServiceImpl(IMediatorService mediatorService) {
        this.mediatorService = mediatorService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        UserDomain user = mediatorService.dispatch(new FindUserByUsernameQuery(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
 
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())  
                .authorities("ROLE_USER")
                .build();
    }
}
