package pl.degath.blog.topic;

import org.springframework.stereotype.Service;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.dto.CreateTopicDto;
import pl.degath.blog.topic.dto.DeleteTopicDto;
import pl.degath.blog.topic.dto.ReadTopicDto;
import pl.degath.blog.topic.dto.UpdateTopicDto;

@Service
public class TopicServiceImpl implements TopicService {

    private final Repository<Topic> topicRepository;

    public TopicServiceImpl(Repository<Topic> topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void createTopic(CreateTopicDto createTopicDto) {
        //todo check if topicDto is null. (invalidInput)
        //todo check if topic exists with given name (NotFound)
        Topic topicToAdd = new Topic(createTopicDto.getTopicName(), createTopicDto.getTopicDescription());

        topicRepository.save(topicToAdd);
    }

    @Override
    public TopicDto readTopic(ReadTopicDto readTopicDto) {
        //todo check if input is null. (invalidInput)
        //todo check if topic with given id exists. (NotFound)

        return topicRepository.findById(readTopicDto.getTopicId())
                .map(TopicDto::new)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateTopic(UpdateTopicDto updateTopicDto) {
        //todo check if topicDto is null (invalidTarget)
        Topic topicToEdit = topicRepository
                .findById(updateTopicDto.getTopicId())
                .orElseThrow(RuntimeException::new); //todo replace with NotFound
        topicToEdit.setName(updateTopicDto.getTopicName());
        topicToEdit.setDescription(updateTopicDto.getTopicDescription());

        topicRepository.save(topicToEdit);
    }

    @Override
    public void deleteTopic(DeleteTopicDto deleteTopicDto) {
        //todo check if topicId is not null (invalidInput)
        //todo check if topic exists with given id

        topicRepository.deleteById(deleteTopicDto.getTopicId());
    }
}


