package pl.degath.blog.topic.command;

import pl.degath.blog.infrastucture.Command;

public class CreateTopicCommand implements Command {
    private final String topicName;
    private final String topicDescription;

    public CreateTopicCommand(String topicName, String topicDescription) {
        this.topicName = topicName;
        this.topicDescription = topicDescription;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }
}
