package com.utp.api.application.command;

import com.utp.api.application.shared.ICommand;

public class CreateNoteCommand implements ICommand {
    private final String title;
    private final String content;
    private final String username;

    public CreateNoteCommand(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Class<? extends ICommand> getCommandType() {
        return CreateNoteCommand.class;
    }
}