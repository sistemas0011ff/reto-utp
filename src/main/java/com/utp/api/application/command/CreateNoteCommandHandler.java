package com.utp.api.application.command;

import com.utp.api.application.shared.ICommandHandler;
import com.utp.api.domain.model.NoteDomain;
import com.utp.api.domain.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateNoteCommandHandler implements ICommandHandler<CreateNoteCommand, NoteDomain> {

    private final NoteRepository noteRepository;

    public CreateNoteCommandHandler(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public NoteDomain handle(CreateNoteCommand command) { 
        NoteDomain noteDomain = new NoteDomain(
            null,  // El ID se genera automáticamente al guardar
            command.getTitle(),
            command.getContent(),
            LocalDateTime.now(),
            command.getUsername()
        );
 
        return noteRepository.save(noteDomain);
    }

    @Override
    public Class<CreateNoteCommand> getCommandType() {
        return CreateNoteCommand.class;
    }
}
