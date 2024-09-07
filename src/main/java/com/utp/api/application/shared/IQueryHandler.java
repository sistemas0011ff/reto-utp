package com.utp.api.application.shared;

public interface IQueryHandler<TQuery extends IQuery<R>, R> {
    R handle(TQuery query);
    Class<TQuery> getQueryType();
}