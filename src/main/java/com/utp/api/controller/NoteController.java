package com.utp.api.controller;

import com.utp.api.application.request.dto.NoteCreateRequestDTO;
import com.utp.api.application.response.dto.ApiResponseDTO;
import com.utp.api.application.response.dto.NoteResponseDTO;
import com.utp.api.application.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<NoteResponseDTO>> createNote(
            @Validated @RequestBody NoteCreateRequestDTO noteRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            NoteResponseDTO createdNote = noteService.createNote(noteRequest, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>("Nota creada exitosamente", HttpStatus.CREATED.value(), createdNote));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<NoteResponseDTO>>> listNotes(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<NoteResponseDTO> notes = noteService.listNotesByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponseDTO<>("Notas obtenidas exitosamente", HttpStatus.OK.value(), notes));
    }
}
