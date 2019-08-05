package pl.degath.blog.topic;

import pl.degath.blog.infrastucture.CommandHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.command.UpdateTopicCommand;

import java.util.Optional;

public class UpdateTopicHandler implements CommandHandler<UpdateTopicCommand> {
    private final Repository<Topic> repository;

    public UpdateTopicHandler(Repository<Topic> repository) {
        this.repository = repository;
    }

    @Override
    public void handle(UpdateTopicCommand updateTopicCommand) {
        Optional.ofNullable(updateTopicCommand)
                .map(this::edit)
                .ifPresentOrElse(repository::save, () -> {
                    throw new InvalidParamsException();
                });
    }


    private Topic edit(UpdateTopicCommand updateTopicCommand) {
        Topic topicToEdit = findById(updateTopicCommand);
        topicToEdit.setName(updateTopicCommand.getTopicName());
        topicToEdit.setDescription(updateTopicCommand.getTopicDescription());

        return topicToEdit;
    }

    private Topic findById(UpdateTopicCommand updateTopicCommand) {
        return repository
                .findById(updateTopicCommand.getTopicId())
                .orElseThrow(NotFoundException::new);
    }
}
