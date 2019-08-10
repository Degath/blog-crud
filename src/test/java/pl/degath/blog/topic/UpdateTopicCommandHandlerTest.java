package pl.degath.blog.topic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.blog.InMemorySpringRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.command.UpdateTopicCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class UpdateTopicCommandHandlerTest {

    private final String newTopicName = "Java";
    private final String newTopicDescription = "The topic contains the basics of how to use the language.";
    private UpdateTopicCommandHandler handler;
    private SpringRepository<Topic> repository;


    @BeforeEach
    void setUp() {
        repository = new InMemorySpringRepository<>();
        handler = new UpdateTopicCommandHandler(repository);
    }

    @Test
    void updateTopic_withValidInput_updatesTheTopic() {
        Topic topic = new TopicBuilder().build();
        UUID existingId = repository.save(topic).getId();
        UpdateTopicCommand input = new UpdateTopicCommand(existingId, newTopicName, newTopicDescription);

        handler.handle(input);

        Topic result = getFirst();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingId);
        assertThat(result.getName()).isEqualTo(newTopicName);
        assertThat(result.getDescription()).isEqualTo(newTopicDescription);
    }

    private Topic getFirst() {
        return repository.findAll().iterator().next();
    }

    @Test
    void updateTopic_withNotExistingId_throwsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        UpdateTopicCommand input = new UpdateTopicCommand(notExistingId, newTopicName, newTopicDescription);

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateTopic_withNullInput_throwsInvalidParams() {
        UpdateTopicCommand input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
