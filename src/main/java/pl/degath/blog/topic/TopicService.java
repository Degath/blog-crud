package pl.degath.blog.topic;

import pl.degath.blog.topic.dto.CreateTopicDto;
import pl.degath.blog.topic.dto.DeleteTopicDto;
import pl.degath.blog.topic.dto.ReadTopicDto;
import pl.degath.blog.topic.dto.UpdateTopicDto;

public interface TopicService {

    void createTopic(CreateTopicDto createTopicDto);

    TopicDto readTopic(ReadTopicDto readTopicDto);

    void updateTopic(UpdateTopicDto updateTopicDto);

    void deleteTopic(DeleteTopicDto deleteTopicDto);
}
