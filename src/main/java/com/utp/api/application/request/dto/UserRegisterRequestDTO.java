package com.utp.api.application.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.utp.api.application.validation.NotDefaultString;

public class UserRegisterRequestDTO {

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre de usuario solo puede contener letras y números")
    @NotDefaultString(message = "El nombre de usuario no puede ser 'string'")
    private String username;

    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "El formato del correo electrónico es inválido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La contraseña debe ser alfanumérica")
    private String password;

    public UserRegisterRequestDTO() {}

    public UserRegisterRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
