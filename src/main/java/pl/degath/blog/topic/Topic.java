package pl.degath.blog.topic;

import pl.degath.blog.infrastucture.EntityRoot;

class Topic extends EntityRoot {

    private String name;
    private String description;

    Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
