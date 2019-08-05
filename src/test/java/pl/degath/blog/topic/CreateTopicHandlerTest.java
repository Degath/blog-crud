package pl.degath.blog.topic;

import org.junit.Before;
import org.junit.Test;
import pl.degath.blog.InMemoryRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.command.CreateTopicCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class CreateTopicHandlerTest {

    private CreateTopicHandler handler;
    private Repository<Topic> repository;

    @Before
    public void setUp() {
        repository = new InMemoryRepository<>();
        handler = new CreateTopicHandler(repository);
    }

    @Test
    public void createTopic_withValidParams_createsTheTopic() {
        String createTopicName = "CRUD - part 1";
        String createTopicDescription = "Basic creation of topic.";
        CreateTopicCommand input = new CreateTopicCommand(createTopicName, createTopicDescription);

        handler.handle(input);

        Topic result = getFirst();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(createTopicName);
        assertThat(result.getDescription()).isEqualTo(createTopicDescription);
    }

    private Topic getFirst() {
        return repository.findAll().iterator().next();
    }

    @Test
    public void createTopic_withNullInput_throwsInvalidParams() {
        CreateTopicCommand input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
