package com.utp.api.domain.repository;

import com.utp.api.domain.model.NoteDomain;

import java.util.List;

public interface NoteRepository {
    NoteDomain save(NoteDomain noteDomain);
    List<NoteDomain> findByUsername(String username);
}