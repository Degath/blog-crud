package pl.degath.blog.topic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.blog.InMemorySpringRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.command.CreateTopicCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class CreateTopicCommandHandlerTest {

    private CreateTopicCommandHandler handler;
    private SpringRepository<Topic> repository;

    @BeforeEach
    void setUp() {
        repository = new InMemorySpringRepository<>();
        handler = new CreateTopicCommandHandler(repository);
    }

    @Test
    void createTopic_withValidParams_createsTheTopic() {
        String createTopicName = "CRUD - part 1";
        String createTopicDescription = "Basic creation of topic.";

        CreateTopicCommand input = new CreateTopicCommand(createTopicName, createTopicDescription);

        var resultUUID = handler.handle(input);

        Topic result = findResult(resultUUID);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(createTopicName);
        assertThat(result.getDescription()).isEqualTo(createTopicDescription);
    }

    private Topic findResult(UUID id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Test
    void createTopic_withNullInput_throwsInvalidParams() {
        CreateTopicCommand input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
