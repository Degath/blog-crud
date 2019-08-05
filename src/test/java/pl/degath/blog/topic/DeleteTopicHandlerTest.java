package pl.degath.blog.topic;

import org.junit.Before;
import org.junit.Test;
import pl.degath.blog.InMemoryRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.command.DeleteTopicCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class DeleteTopicHandlerTest {

    private DeleteTopicHandler handler;
    private Repository<Topic> repository;

    @Before
    public void setUp() {
        repository = new InMemoryRepository<>();
        handler = new DeleteTopicHandler(repository);
    }

    @Test
    public void deleteTopic_withExistingId_deletesTopic() {
        UUID existingId = repository.save(new Topic("CRUD - part 4", "Basic deletion of topic.")).getId();
        DeleteTopicCommand input = new DeleteTopicCommand(existingId);

        handler.handle(input);

        assertThat(hasTopics()).isFalse();
    }

    private boolean hasTopics() {
        return repository.findAll().iterator().hasNext();
    }


    @Test
    public void deleteTopic_withNotExistingId_throwsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        DeleteTopicCommand input = new DeleteTopicCommand(notExistingId);

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleteTopic_withNotExistingId_throwsInvalidParams() {
        DeleteTopicCommand input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
