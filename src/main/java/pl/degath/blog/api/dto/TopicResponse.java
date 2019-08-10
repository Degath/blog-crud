package pl.degath.blog.api.dto;

import pl.degath.blog.topic.TopicDto;

import java.util.UUID;

public class TopicResponse {

    private final UUID id;
    private final String name;
    private final String description;

    public TopicResponse(TopicDto topicDto) {
        this.id = topicDto.getId();
        this.name = topicDto.getName();
        this.description = topicDto.getDescription();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
