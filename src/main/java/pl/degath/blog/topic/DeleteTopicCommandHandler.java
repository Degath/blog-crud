package pl.degath.blog.topic;

import org.springframework.stereotype.Service;
import pl.degath.blog.infrastucture.CommandHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.command.DeleteTopicCommand;

import java.util.Optional;

@Service
public class DeleteTopicCommandHandler implements CommandHandler<DeleteTopicCommand> {

    private final SpringRepository<Topic> repository;

    public DeleteTopicCommandHandler(SpringRepository<Topic> repository) {
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
