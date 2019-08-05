package pl.degath.blog.topic;

import org.springframework.stereotype.Service;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.dto.CreateTopicDto;
import pl.degath.blog.topic.dto.DeleteTopicDto;
import pl.degath.blog.topic.dto.ReadTopicDto;
import pl.degath.blog.topic.dto.UpdateTopicDto;

import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final Repository<Topic> topicRepository;

    TopicServiceImpl(Repository<Topic> topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void createTopic(CreateTopicDto createTopicDto) {
        Optional.ofNullable(createTopicDto)
                .map(this::create)
                .ifPresentOrElse(topicRepository::save, () -> {
                    throw new InvalidParamsException();
                });
    }

    private Topic create(CreateTopicDto createTopicDto) {
        return new Topic(createTopicDto.getTopicName(), createTopicDto.getTopicDescription());
    }

    @Override
    public TopicDto readTopic(ReadTopicDto readTopicDto) {
        return Optional.ofNullable(readTopicDto)
                .map(this::findById)
                .map(TopicDto::new)
                .orElseThrow(InvalidParamsException::new);
    }

    private Topic findById(ReadTopicDto readTopicDto) {
        return topicRepository.findById(readTopicDto.getTopicId())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void updateTopic(UpdateTopicDto updateTopicDto) {
        Optional.ofNullable(updateTopicDto)
                .map(this::edit)
                .ifPresentOrElse(topicRepository::save, () -> {
                    throw new InvalidParamsException();
                });
    }

    private Topic edit(UpdateTopicDto updateTopicDto) {
        Topic topicToEdit = findById(updateTopicDto);
        topicToEdit.setName(updateTopicDto.getTopicName());
        topicToEdit.setDescription(updateTopicDto.getTopicDescription());

        return topicToEdit;
    }

    private Topic findById(UpdateTopicDto updateTopicDto) {
        return topicRepository
                .findById(updateTopicDto.getTopicId())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteTopic(DeleteTopicDto deleteTopicDto) {
        Optional.ofNullable(deleteTopicDto)
                .map(this::findById)
                .ifPresentOrElse(topicRepository::delete, () -> {
                    throw new InvalidParamsException();
                });
    }

    private Topic findById(DeleteTopicDto deleteTopicDto) {
        return topicRepository.findById(deleteTopicDto.getTopicId())
                .orElseThrow(NotFoundException::new);
    }
}
