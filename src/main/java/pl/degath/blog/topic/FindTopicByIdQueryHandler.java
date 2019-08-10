package pl.degath.blog.topic;

import org.springframework.stereotype.Service;
import pl.degath.blog.infrastucture.QueryHandler;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.query.FindTopicByIdQuery;

import java.util.Optional;

@Service
public class FindTopicByIdQueryHandler implements QueryHandler<FindTopicByIdQuery, TopicDto> {

    private final SpringRepository<Topic> repository;

    public FindTopicByIdQueryHandler(SpringRepository<Topic> repository) {
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