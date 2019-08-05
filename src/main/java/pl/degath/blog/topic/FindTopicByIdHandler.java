package pl.degath.blog.topic;

import pl.degath.blog.infrastucture.QueryHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.query.FindTopicByIdQuery;

import java.util.Optional;

public class FindTopicByIdHandler implements QueryHandler<FindTopicByIdQuery, TopicDto> {

    private final Repository<Topic> repository;

    public FindTopicByIdHandler(Repository<Topic> repository) {
        this.repository = repository;
    }

    @Override
    public TopicDto handle(FindTopicByIdQuery findTopicByIdQuery) {
        return Optional.ofNullable(findTopicByIdQuery)
                .map(this::findById)
                .map(TopicDto::new)
                .orElseThrow(InvalidParamsException::new);
    }

    private Topic findById(FindTopicByIdQuery findTopicByIdQuery) {
        return repository.findById(findTopicByIdQuery.getTopicId())
                .orElseThrow(NotFoundException::new);
    }
}
