package pl.degath.blog.topic;

import org.junit.Before;
import org.junit.Test;
import pl.degath.blog.InMemoryRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.command.UpdateTopicCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class UpdateTopicHandlerTest {

    private UpdateTopicHandler handler;
    private Repository<Topic> repository;

    @Before
    public void setUp() {
        repository = new InMemoryRepository<>();
        handler = new UpdateTopicHandler(repository);
    }

    @Test
    public void updateTopic_withValidInput_updatesTheTopic() {
        UUID existingId = repository.save(new Topic("CRUD - part 1", "Basic creation of topic.")).getId();
        String newTopicName = "CRUD - part 3";
        String newTopicDescription = "CRUD - part 3";
        UpdateTopicCommand input = new UpdateTopicCommand(existingId, newTopicName, newTopicDescription);

        handler.handle(input);

        Topic result = getFirst();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(newTopicName);
        assertThat(result.getDescription()).isEqualTo(newTopicDescription);
    }

    private Topic getFirst() {
        return repository.findAll().iterator().next();
    }

    @Test
    public void updateTopic_withNotExistingId_throwsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        UpdateTopicCommand input = new UpdateTopicCommand(notExistingId, "CRUD - part 3", "CRUD - part 3");

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void updateTopic_withNullInput_throwsInvalidParams() {
        UpdateTopicCommand input = null;

        Throwable thrown = catchThrowable(() -> handler.handle(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
}
