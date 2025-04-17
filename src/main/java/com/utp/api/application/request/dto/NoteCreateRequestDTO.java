package com.utp.api.application.request.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NoteCreateRequestDTO {
    @NotNull(message = "La calificación es obligatoria")
    @DecimalMin(value = "0.0", inclusive = true, message = "La calificación no puede ser menor a 0")
    @DecimalMax(value = "20.0", inclusive = true, message = "La calificación no puede ser mayor a 20")
    private Double score;
    
    @Size(max = 500, message = "La descripción debe tener máximo 500 caracteres")
    private String description;
    
    // Getters y Setters
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}