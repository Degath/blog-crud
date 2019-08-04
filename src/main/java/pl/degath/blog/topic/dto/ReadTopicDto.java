package pl.degath.blog.topic.dto;

import java.util.UUID;

public class ReadTopicDto {
    private final UUID topicId;

    public ReadTopicDto(UUID topicId) {
        this.topicId = topicId;
    }

    public UUID getTopicId() {
        return topicId;
    }
}
