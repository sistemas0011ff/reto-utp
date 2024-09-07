package com.utp.api.infraestructure.util;

import com.utp.api.domain.model.UserDomain;
import com.utp.api.infraestructure.repository.UserEntity;

public class UserMapper {
 
    public static UserEntity toPersistence(UserDomain userDomain) {
        return new UserEntity(
                userDomain.getId(),
                userDomain.getUsername(),
                userDomain.getEmail(),
                userDomain.getPassword()
        );
    }
 
    public static UserDomain toDomain(UserEntity userPersistence) {
        return new UserDomain(
                userPersistence.getId(),
                userPersistence.getUsername(),
                userPersistence.getEmail(),
                userPersistence.getPassword()
        );
    }
}