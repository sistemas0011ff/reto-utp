package com.utp.api.controller;

import com.utp.api.application.request.dto.LoginRequestDTO;
import com.utp.api.application.request.dto.UserRegisterRequestDTO;
import com.utp.api.application.response.dto.ApiResponseDTO;
import com.utp.api.application.response.dto.UserResponseDTO;
import com.utp.api.application.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> registerUser(@Valid @RequestBody UserRegisterRequestDTO userRegisterDTO) {
        try {
            UserResponseDTO registeredUser = userService.registerUser(userRegisterDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>("Usuario registrado exitosamente", HttpStatus.CREATED.value(), registeredUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> loginUser(@Valid @RequestBody LoginRequestDTO loginDTO) {
        try {
            String token = userService.loginUser(loginDTO);
            return ResponseEntity.ok(new ApiResponseDTO<>("Inicio de sesión exitoso", HttpStatus.OK.value(), token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }
}
