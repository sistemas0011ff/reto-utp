package com.utp.api.infraestructure.repository;

import com.utp.api.domain.model.NoteDomain;
import com.utp.api.domain.repository.NoteRepository;
import com.utp.api.infraestructure.util.NoteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NoteRepositoryImpl implements NoteRepository {

    private final JpaNoteRepository jpaNoteRepository;
    private final JpaUserRepository jpaUserRepository;

    public NoteRepositoryImpl(JpaNoteRepository jpaNoteRepository, JpaUserRepository jpaUserRepository) {
        this.jpaNoteRepository = jpaNoteRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<NoteDomain> findByUsername(String username) {
        // Utiliza la nueva relación para buscar notas por el 'username' del usuario asociado
        return jpaNoteRepository.findByUser_Username(username)
                .stream()
                .map(NoteMapper::toDomain) // Convierte NoteEntity a NoteDomain
                .collect(Collectors.toList());
    }

    @Override
    public NoteDomain save(NoteDomain noteDomain) {
        UserEntity userEntity = jpaUserRepository.findByUsername(noteDomain.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return NoteMapper.toDomain(jpaNoteRepository.save(NoteMapper.toPersistence(noteDomain, userEntity)));
    }
}
