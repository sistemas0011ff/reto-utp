package com.utp.api.application.query;

import com.utp.api.domain.model.NoteDomain;
import com.utp.api.domain.repository.NoteRepository;
import com.utp.api.application.shared.IQueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindNotesByUsernameQueryHandler implements IQueryHandler<FindNotesByUsernameQuery, List<NoteDomain>> {

    private final NoteRepository noteRepository;

    public FindNotesByUsernameQueryHandler(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<NoteDomain> handle(FindNotesByUsernameQuery query) {
        return noteRepository.findByUsername(query.getUsername());
    }

    @Override
    public Class<FindNotesByUsernameQuery> getQueryType() {
        return FindNotesByUsernameQuery.class;
    }
}