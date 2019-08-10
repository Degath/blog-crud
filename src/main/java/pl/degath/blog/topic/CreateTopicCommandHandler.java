package pl.degath.blog.topic;

import org.springframework.stereotype.Service;
import pl.degath.blog.infrastucture.CreateCommandHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.command.CreateTopicCommand;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateTopicCommandHandler implements CreateCommandHandler<CreateTopicCommand> {

    private final SpringRepository<Topic> repository;

    public CreateTopicCommandHandler(SpringRepository<Topic> repository) {
        this.repository = repository;
    }

    @Override
    public UUID handle(CreateTopicCommand command) {
        return Optional.ofNullable(command)
                .map(this::createTopic)
                .map(this::saveTopic)
                .orElseThrow(InvalidParamsException::new);
    }

    private Topic createTopic(CreateTopicCommand createTopicCommand) {
        return new Topic(createTopicCommand.getTopicName(), createTopicCommand.getTopicDescription());
    }

    private UUID saveTopic(Topic topic) {
        return repository.save(topic).getId();
    }
}
