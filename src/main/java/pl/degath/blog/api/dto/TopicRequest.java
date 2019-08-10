package pl.degath.blog.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicRequest {

    private final String topicName;
    private final String topicDescription;

    @JsonCreator
    public TopicRequest(@JsonProperty("topicName") String topicName,
                        @JsonProperty("topicDescription") String topicDescription) {
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
