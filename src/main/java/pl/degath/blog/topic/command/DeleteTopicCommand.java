package pl.degath.blog.topic.command;

import pl.degath.blog.infrastucture.Command;

import java.util.UUID;

public class DeleteTopicCommand implements Command {
    private final UUID topicId;

    public DeleteTopicCommand(UUID topicId) {
        this.topicId = topicId;
    }

    public UUID getTopicId() {
        return topicId;
    }
}
