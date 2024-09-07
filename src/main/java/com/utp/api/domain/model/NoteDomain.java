package com.utp.api.domain.model;

import java.time.LocalDateTime;

public class NoteDomain {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String username;  

    public NoteDomain(Long id, String title, String content, LocalDateTime createdAt, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
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