package com.utp.api.application.service.impl;

import com.utp.api.application.command.CreateUserCommand;
import com.utp.api.application.query.FindUserByUsernameQuery;
import com.utp.api.application.request.dto.UserRegisterRequestDTO;
import com.utp.api.application.request.dto.LoginRequestDTO;
import com.utp.api.application.response.dto.UserResponseDTO;
import com.utp.api.application.service.UserService;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.infraestructure.util.IMediatorService;
import com.utp.api.shared.util.IJwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IMediatorService mediatorService;
    private final PasswordEncoder passwordEncoder;
    private final IJwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(IMediatorService mediatorService, PasswordEncoder passwordEncoder, IJwtTokenProvider jwtTokenProvider) {
        this.mediatorService = mediatorService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserResponseDTO registerUser(UserRegisterRequestDTO userDto) {  
        Optional<UserDomain> existingUser = mediatorService.dispatch(new FindUserByUsernameQuery(userDto.getUsername()));

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }
 
        CreateUserCommand command = new CreateUserCommand(
            userDto.getUsername(),
            userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword())
        );
 
        UserDomain userDomain = mediatorService.dispatch(command);
 
        return new UserResponseDTO(
            userDomain.getId(),
            userDomain.getUsername(),
            userDomain.getEmail()
        );
    }

    @Override
    public String loginUser(LoginRequestDTO loginDto) {
 
        UserDomain user = mediatorService.dispatch(new FindUserByUsernameQuery(loginDto.getUsername()))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
 
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
 
        return jwtTokenProvider.createToken(user.getUsername());
    }
}
