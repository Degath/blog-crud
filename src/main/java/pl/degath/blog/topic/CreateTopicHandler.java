package pl.degath.blog.topic;

import pl.degath.blog.infrastucture.CommandHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.command.CreateTopicCommand;

import java.util.Optional;

public class CreateTopicHandler implements CommandHandler<CreateTopicCommand> {

    private final Repository<Topic> repository;

    public CreateTopicHandler(Repository<Topic> repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CreateTopicCommand command) {
        Optional.ofNullable(command)
                .map(this::create)
                .ifPresentOrElse(repository::save, () -> {
                    throw new InvalidParamsException();
                });
    }

    private Topic create(CreateTopicCommand createTopicCommand) {
        return new Topic(createTopicCommand.getTopicName(), createTopicCommand.getTopicDescription());
    }
}
