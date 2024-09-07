package com.utp.api.application.query;

import java.util.List;
import com.utp.api.application.shared.IQuery;
import com.utp.api.domain.model.NoteDomain;

public class FindNotesByUsernameQuery implements IQuery<List<NoteDomain>> {
    private final String username;

    public FindNotesByUsernameQuery(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}