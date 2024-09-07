package com.utp.api.application.service.impl;

import com.utp.api.application.command.CreateNoteCommand;
import com.utp.api.application.query.FindNotesByUsernameQuery;
import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.NoteResponseDTO;
import com.utp.api.application.service.NoteService;
import com.utp.api.domain.model.NoteDomain;
import com.utp.api.infraestructure.util.IMediatorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final IMediatorService mediatorService;

    public NoteServiceImpl(IMediatorService mediatorService) {
        this.mediatorService = mediatorService;
    }

    @Override
    public NoteResponseDTO createNote(NoteCreateRequestDTO requestDTO, String username) {

        CreateNoteCommand command = new CreateNoteCommand(
                requestDTO.getTitle(),
                requestDTO.getContent(),
                username
        );

        NoteDomain noteDomain = mediatorService.dispatch(command);

        return new NoteResponseDTO(
                noteDomain.getId(),
                noteDomain.getTitle(),
                noteDomain.getContent(),
                noteDomain.getCreatedAt(),
                noteDomain.getUsername()
        );
    }

    @Override
    public List<NoteResponseDTO> listNotesByUsername(String username) {
    
        FindNotesByUsernameQuery query = new FindNotesByUsernameQuery(username);

        List<NoteDomain> notes = mediatorService.dispatch(query);

        return notes.stream()
                .map(note -> new NoteResponseDTO(
                        note.getId(),
                        note.getTitle(),
                        note.getContent(),
                        note.getCreatedAt(),
                        note.getUsername()))
                .collect(Collectors.toList());
    }
}
