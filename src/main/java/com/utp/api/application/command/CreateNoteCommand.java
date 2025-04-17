package com.utp.api.application.command;

import com.utp.api.application.shared.ICommand;

public class CreateNoteCommand implements ICommand {
    private final Double score;
    private final String description;
    private final String username;

    public CreateNoteCommand(Double score, String description, String username) {
        this.score = score;
        this.description = description;
        this.username = username;
    }

    public Double getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Class<? extends ICommand> getCommandType() {
        return CreateNoteCommand.class;
    }
}