package pl.degath.blog.topic.dto;

import java.util.UUID;

public class UpdateTopicDto {
    private final UUID topicId;
    private final String topicName;
    private final String topicDescription;

    public UpdateTopicDto(UUID topicId, String topicName, String topicDescription) {
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
