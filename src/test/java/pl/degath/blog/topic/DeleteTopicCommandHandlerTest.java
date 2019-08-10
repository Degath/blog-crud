package pl.degath.blog.topic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.blog.InMemorySpringRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.command.DeleteTopicCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class DeleteTopicCommandHandlerTest {

    private DeleteTopicCommandHandler handler;
    private SpringRepository<Topic> repository;

    @BeforeEach
    void setUp() {
        repository = new InMemorySpringRepository<>();
        handler = new DeleteTopicCommandHandler(repository);
    }

    @Test
    void deleteTopic_withExistingId_deletesTopic() {
        UUID existingId = repository.save(new TopicBuilder().build()).getId();
        DeleteTopicCommand input = new DeleteTopicCommand(existingId);

        handler.handle(input);

        assertThat(hasTopics()).isFalse();
    }

    private boolean hasTopics() {
        return repository.findAll().iterator().hasNext();
    }


    @Test
    void deleteTopic_withNotExistingId_throwsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        DeleteTopicCommand input = new DeleteTopicCommand(notExistingId);

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteTopic_withNotExistingId_throwsInvalidParams() {
        DeleteTopicCommand input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
