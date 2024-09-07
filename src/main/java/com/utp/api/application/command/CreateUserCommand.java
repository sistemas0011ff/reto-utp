package com.utp.api.application.command;

import com.utp.api.application.shared.ICommand;

public class CreateUserCommand implements ICommand {
    private final String username;
    private final String email;
    private final String password;

    public CreateUserCommand(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Class<? extends ICommand> getCommandType() {
        return CreateUserCommand.class;
    }
}