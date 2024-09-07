package com.utp.api.infraestructure.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.utp.api.application.shared.ICommand;
import com.utp.api.application.shared.ICommandHandler;
import com.utp.api.application.shared.IQuery;
import com.utp.api.application.shared.IQueryHandler;



 
@Service
public class MediatorService implements IMediatorService{

    private final Map<Class<? extends ICommand>, ICommandHandler<? extends ICommand, ?>> commandHandlers;
    private final Map<Class<? extends IQuery<?>>, IQueryHandler<?, ?>> queryHandlers;
 
    public MediatorService(List<ICommandHandler<? extends ICommand, ?>> commandHandlers,
                           List<IQueryHandler<?, ?>> queryHandlers) {
        this.commandHandlers = new HashMap<>();
        this.queryHandlers = new HashMap<>();

        commandHandlers.forEach(handler -> {
            Class<? extends ICommand> commandType = handler.getCommandType();
            this.commandHandlers.put(commandType, handler);
        });

        queryHandlers.forEach(handler -> {
            Class<? extends IQuery<?>> queryType = handler.getQueryType();
            this.queryHandlers.put(queryType, handler);
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends ICommand, R> R dispatch(T command) {
        ICommandHandler<T, R> handler = (ICommandHandler<T, R>) commandHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for command: " + command.getClass().getName());
        }
        return handler.handle(command);
    }

    @SuppressWarnings("unchecked")
    public <T extends IQuery<R>, R> R dispatch(T query) {
        IQueryHandler<T, R> handler = (IQueryHandler<T, R>) queryHandlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for query: " + query.getClass().getName());
        }
        return handler.handle(query);
    }
}