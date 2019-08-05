package pl.degath.blog.topic;

import java.util.UUID;

class TopicDto {

    private final UUID id;
    private final String name;
    private final String description;

    TopicDto(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.description = topic.getDescription();
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }
}
