package com.utp.api.domain.repository;

import java.util.Optional;

import com.utp.api.domain.model.UserDomain; 


public interface UserRepository {
    Optional<UserDomain> findByUsername(String username);
    UserDomain save(UserDomain user); 
}
