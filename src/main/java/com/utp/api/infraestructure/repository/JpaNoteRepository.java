package com.utp.api.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByUser_Username(String username);  
}
