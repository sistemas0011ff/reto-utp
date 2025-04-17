package com.utp.api.application.response.dto;

import java.time.LocalDateTime;

public class NoteResponseDTO {
    private Long id;
    private Double score;
    private String description;
    private LocalDateTime createdAt;
    private String username;
    
    public NoteResponseDTO() {
    }
    
    public NoteResponseDTO(Long id, Double score, String description, LocalDateTime createdAt, String username) {
        this.id = id;
        this.score = score;
        this.description = description;
        this.createdAt = createdAt;
        this.username = username;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}