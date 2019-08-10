package pl.degath.blog.topic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.blog.InMemorySpringRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.query.FindTopicByIdQuery;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class FindTopicByIdQueryHandlerTest {

    private FindTopicByIdQueryHandler handler;
    private SpringRepository<Topic> repository;

    @BeforeEach
    void setUp() {
        repository = new InMemorySpringRepository<>();
        handler = new FindTopicByIdQueryHandler(repository);
    }

    @Test
    void readTopic_WithExistingId_ReturnsTopic() {
        Topic topic = new TopicBuilder().build();
        UUID existingId = repository.save(topic).getId();
        FindTopicByIdQuery input = new FindTopicByIdQuery(existingId);

        TopicDto result = handler.handle(input);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(topic.getId());
        assertThat(result.getName()).isEqualTo(topic.getName());
        assertThat(result.getDescription()).isEqualTo(topic.getDescription());
    }

    @Test
    void readTopic_WithNotExistingId_ThrowsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        FindTopicByIdQuery input = new FindTopicByIdQuery(notExistingId);

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void readTopic_WithNullInput_ThrowsInvalidParams() {
        FindTopicByIdQuery input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
