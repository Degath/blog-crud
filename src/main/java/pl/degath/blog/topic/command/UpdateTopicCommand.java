package pl.degath.blog.topic.command;

import pl.degath.blog.infrastucture.Command;

import java.util.UUID;

public class UpdateTopicCommand implements Command {
    private final UUID topicId;
    private final String topicName;
    private final String topicDescription;

    public UpdateTopicCommand(UUID topicId, String topicName, String topicDescription) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }
}
