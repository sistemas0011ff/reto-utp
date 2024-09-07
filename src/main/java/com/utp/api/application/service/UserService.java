package com.utp.api.application.service;

import com.utp.api.application.request.dto.LoginRequestDTO;
import com.utp.api.application.request.dto.UserRegisterRequestDTO;
import com.utp.api.application.response.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRegisterRequestDTO userDto);
    String loginUser(LoginRequestDTO loginDto);
}

