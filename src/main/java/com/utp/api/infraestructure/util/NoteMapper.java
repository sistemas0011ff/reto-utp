package com.utp.api.infraestructure.util;

import com.utp.api.domain.model.NoteDomain;
import com.utp.api.infraestructure.repository.NoteEntity;
import com.utp.api.infraestructure.repository.UserEntity;

public class NoteMapper {

    
    public static NoteDomain toDomain(NoteEntity noteEntity) {
        return new NoteDomain(
                noteEntity.getId(),
                noteEntity.getTitle(),
                noteEntity.getContent(),
                noteEntity.getCreatedAt(),
                noteEntity.getUser().getUsername() 
        );
    }

    
    public static NoteEntity toPersistence(NoteDomain noteDomain, UserEntity userEntity) {
        NoteEntity noteEntity = new NoteEntity(
                noteDomain.getTitle(),
                noteDomain.getContent(),
                noteDomain.getCreatedAt(),
                userEntity  
        );

        
        if (noteDomain.getId() != null) {
            noteEntity.setId(noteDomain.getId());
        }

        return noteEntity;
    }
}
