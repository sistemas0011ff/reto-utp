package com.utp.api.application.service;
 
import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.NoteResponseDTO;

import java.util.List;

public interface NoteService {
    NoteResponseDTO createNote(NoteCreateRequestDTO requestDTO, String username); 
    List<NoteResponseDTO> listNotesByUsername(String username); 
}