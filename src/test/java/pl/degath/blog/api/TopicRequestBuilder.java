package pl.degath.blog.api;

import pl.degath.blog.api.dto.TopicRequest;

public class TopicRequestBuilder {

    private String topicName;
    private String topicDescription;

    TopicRequestBuilder() {
        topicName = "Spring basics.";
        topicDescription = "The topic contains the basics of how to use the framework.";
    }

    TopicRequestBuilder withTopicName(String newName) {
        topicName = newName;
        return this;

    }

    public TopicRequestBuilder withTopicDescription(String newDescription) {
        topicDescription = newDescription;
        return this;
    }

    TopicRequest build() {
        return new TopicRequest(topicName, topicDescription);
    }
}
