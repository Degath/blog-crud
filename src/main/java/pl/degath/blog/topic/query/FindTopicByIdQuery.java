package pl.degath.blog.topic.query;

import pl.degath.blog.infrastucture.Query;

import java.util.UUID;

public class FindTopicByIdQuery implements Query {
    private final UUID topicId;

    public FindTopicByIdQuery(UUID topicId) {
        this.topicId = topicId;
    }

    public UUID getTopicId() {
        return topicId;
    }
}
