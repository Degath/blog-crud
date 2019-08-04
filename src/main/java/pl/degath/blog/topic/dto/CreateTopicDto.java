package pl.degath.blog.topic.dto;

public class CreateTopicDto {
    private final String topicName;
    private final String topicDescription;

    public CreateTopicDto(String topicName, String topicDescription) {
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
