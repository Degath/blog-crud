package pl.degath.blog.topic.dto;

import java.util.UUID;

public class DeleteTopicDto {
    private final UUID topicId;

    public DeleteTopicDto(UUID topicId) {
        this.topicId = topicId;
    }

    public UUID getTopicId() {
        return topicId;
    }
}
