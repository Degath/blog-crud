package pl.degath.blog.infrastucture;

import java.util.UUID;

public interface CreateCommandHandler<T extends Command> {

    UUID handle(T command);
}