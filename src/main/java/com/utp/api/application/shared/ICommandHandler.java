package com.utp.api.application.shared;

public interface ICommandHandler<T extends ICommand, R> {
    R handle(T command);
    Class<T> getCommandType();
}