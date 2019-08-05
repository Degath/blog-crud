package pl.degath.blog.infrastucture;

public interface CommandHandler<T extends Command> {

    void handle(T command);
}
