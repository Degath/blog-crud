package pl.degath.blog.infrastucture;

public interface QueryHandler<T extends Query, R> {

    R handle(T query);
}

