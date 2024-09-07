package com.utp.api.application.shared;

public interface ICommand {
    Class<? extends ICommand> getCommandType();
}
