package com.utp.api.application.command;

import com.utp.api.application.shared.ICommandHandler;
import com.utp.api.domain.model.UserDomain;
import com.utp.api.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCommandHandler implements ICommandHandler<CreateUserCommand, UserDomain> {

    private final UserRepository userRepository; 

    public CreateUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository; 
    }

    @Override
    public UserDomain handle(CreateUserCommand command) {
       
        UserDomain userDomain = new UserDomain(
            command.getUsername(),
            command.getEmail(),
            command.getPassword()
        );
 
        return userRepository.save(userDomain);
    }

    @Override
    public Class<CreateUserCommand> getCommandType() {
        return CreateUserCommand.class;
    }
}
