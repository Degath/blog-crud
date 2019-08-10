package pl.degath.blog.topic;

public final class TopicBuilder {

    private String name;
    private String description;

    public TopicBuilder() {
        name = "Spring basics.";
        description = "The topic contains the basics of how to use the framework.";
    }

    public TopicBuilder withName(String newName) {
        name = newName;
        return this;

    }

    public TopicBuilder withDescription(String newDescription) {
        description = newDescription;
        return this;
    }

    public Topic build() {
        return new Topic(name, description);
    }
}
