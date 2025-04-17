package com.utp.api.infraestructure.util;

import com.utp.api.domain.model.NoteDomain;
import com.utp.api.infraestructure.repository.NoteEntity;
import com.utp.api.infraestructure.repository.UserEntity;

public class NoteMapper {
    
    public static NoteDomain toDomain(NoteEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new NoteDomain(
            entity.getId(),
            entity.getScore(),
            entity.getDescription(),
            entity.getCreatedAt(),
            entity.getUser().getUsername()
        );
    }
    
    public static NoteEntity toPersistence(NoteDomain domain, UserEntity userEntity) {
        if (domain == null) {
            return null;
        }
        
        NoteEntity entity = new NoteEntity(
            domain.getScore(),
            domain.getDescription(),
            domain.getCreatedAt(),
            userEntity
        );
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        return entity;
    }
}