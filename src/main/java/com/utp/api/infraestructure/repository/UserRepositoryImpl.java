package com.utp.api.infraestructure.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.domain.repository.UserRepository;
import com.utp.api.infraestructure.util.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<UserDomain> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(UserMapper::toDomain); 
    }

    @Override
    public UserDomain save(UserDomain userDomain) { 
        return UserMapper.toDomain(jpaUserRepository.save(UserMapper.toPersistence(userDomain)));
    }
}
