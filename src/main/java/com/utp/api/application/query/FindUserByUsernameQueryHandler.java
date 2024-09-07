package com.utp.api.application.query;

import com.utp.api.domain.model.UserDomain;
import com.utp.api.domain.repository.UserRepository;
import com.utp.api.application.shared.IQueryHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindUserByUsernameQueryHandler implements IQueryHandler<FindUserByUsernameQuery, Optional<UserDomain>> {

    private final UserRepository userRepository;

    public FindUserByUsernameQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDomain> handle(FindUserByUsernameQuery query) {
        return userRepository.findByUsername(query.getUsername());
    }

    @Override
    public Class<FindUserByUsernameQuery> getQueryType() {
        return FindUserByUsernameQuery.class;
    }
}
