package com.utp.api.application.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.utp.api.application.validation.NotDefaultString;

public class NoteCreateRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    @NotDefaultString(message = "El título no puede ser 'string'")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 3, max = 500, message = "El contenido debe tener entre 3 y 500 caracteres")
    @NotDefaultString(message = "El contenido no puede ser 'string'")
    private String content;

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
