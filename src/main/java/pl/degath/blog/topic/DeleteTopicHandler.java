package pl.degath.blog.topic;

import pl.degath.blog.infrastucture.CommandHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.command.DeleteTopicCommand;

import java.util.Optional;

public class DeleteTopicHandler implements CommandHandler<DeleteTopicCommand> {

    private final Repository<Topic> repository;

    public DeleteTopicHandler(Repository<Topic> repository) {
        this.repository = repository;
    }

    @Override
    public void handle(DeleteTopicCommand deleteTopicCommand) {
        Optional.ofNullable(deleteTopicCommand)
                .map(this::findById)
                .ifPresentOrElse(repository::delete, () -> {
                    throw new InvalidParamsException();
                });

    }

    private Topic findById(DeleteTopicCommand deleteTopicCommand) {
        return repository.findById(deleteTopicCommand.getTopicId())
                .orElseThrow(NotFoundException::new);
    }
}
