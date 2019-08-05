package pl.degath.blog.topic;

import org.junit.Before;
import org.junit.Test;
import pl.degath.blog.InMemoryRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.query.FindTopicByIdQuery;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class FindTopicByIdHandlerTest {

    private FindTopicByIdHandler handler;
    private Repository<Topic> repository;

    @Before
    public void setUp() {
        repository = new InMemoryRepository<>();
        handler = new FindTopicByIdHandler(repository);
    }

    @Test
    public void readTopic_WithExistingId_ReturnsTopic() {
        String readName = "CRUD - part 2";
        String readDescription = "Basic creation of topic.";
        UUID existingId = repository.save(new Topic(readName, readDescription)).getId();
        FindTopicByIdQuery input = new FindTopicByIdQuery(existingId);

        TopicDto result = handler.handle(input);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(readName);
        assertThat(result.getDescription()).isEqualTo(readDescription);
    }

    @Test
    public void readTopic_WithNotExistingId_ThrowsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        FindTopicByIdQuery input = new FindTopicByIdQuery(notExistingId);

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void readTopic_WithNullInput_ThrowsInvalidParams() {
        FindTopicByIdQuery input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
