package com.utp.api.infraestructure.util;

import com.utp.api.application.shared.ICommand;
import com.utp.api.application.shared.IQuery;

public interface IMediatorService {
    <T extends ICommand, R> R dispatch(T command);
    <T extends IQuery<R>, R> R dispatch(T query);
}