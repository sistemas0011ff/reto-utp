package com.utp.api.application.query;

import java.util.Optional;

import com.utp.api.application.shared.IQuery;
import com.utp.api.domain.model.UserDomain;

public class FindUserByUsernameQuery implements IQuery<Optional<UserDomain>> {
    private final String username;

    public FindUserByUsernameQuery(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}