package pl.degath.blog.topic;

import org.junit.Before;
import org.junit.Test;
import pl.degath.blog.InMemoryRepository;
import pl.degath.blog.infrastucture.exception.InvalidParamsException;
import pl.degath.blog.infrastucture.exception.NotFoundException;
import pl.degath.blog.port.Repository;
import pl.degath.blog.topic.dto.CreateTopicDto;
import pl.degath.blog.topic.dto.DeleteTopicDto;
import pl.degath.blog.topic.dto.ReadTopicDto;
import pl.degath.blog.topic.dto.UpdateTopicDto;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class TopicServiceImplTest {

    private Repository<Topic> repository;
    private TopicService service;

    @Before
    public void setUp() {
        this.repository = new InMemoryRepository<>();
        this.service = new TopicServiceImpl(repository);
    }

    //region create topic tests
    @Test
    public void createTopic_withValidParams_createsTheTopic() {
        String createTopicName = "CRUD - part 1";
        String createTopicDescription = "Basic creation of topic.";
        CreateTopicDto input = new CreateTopicDto(createTopicName, createTopicDescription);

        service.createTopic(input);

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
        CreateTopicDto input = null;

        Throwable thrown = catchThrowable(() -> service.createTopic(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
    //endregion

    //region read topic tests
    @Test
    public void readTopic_WithExistingId_ReturnsTopic() {
        String readName = "CRUD - part 2";
        String readDescription = "Basic creation of topic.";
        UUID existingId = repository.save(new Topic(readName, readDescription)).getId();
        ReadTopicDto input = new ReadTopicDto(existingId);

        TopicDto result = service.readTopic(input);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(readName);
        assertThat(result.getDescription()).isEqualTo(readDescription);
    }

    @Test
    public void readTopic_WithNotExistingId_ThrowsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        ReadTopicDto input = new ReadTopicDto(notExistingId);

        Throwable thrown = catchThrowable(() -> service.readTopic(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void readTopic_WithNullInput_ThrowsInvalidParams() {
        ReadTopicDto input = null;

        Throwable thrown = catchThrowable(() -> service.readTopic(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
    //endregion

    //region update topic tests
    @Test
    public void updateTopic_withValidInput_updatesTheTopic() {
        UUID existingId = repository.save(new Topic("CRUD - part 1", "Basic creation of topic.")).getId();
        String newTopicName = "CRUD - part 3";
        String newTopicDescription = "CRUD - part 3";
        UpdateTopicDto input = new UpdateTopicDto(existingId, newTopicName, newTopicDescription);

        service.updateTopic(input);

        Topic result = getFirst();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(newTopicName);
        assertThat(result.getDescription()).isEqualTo(newTopicDescription);
    }

    @Test
    public void updateTopic_withNotExistingId_throwsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        UpdateTopicDto input = new UpdateTopicDto(notExistingId, "CRUD - part 3", "CRUD - part 3");

        Throwable thrown = catchThrowable(() -> service.updateTopic(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void updateTopic_withNullInput_throwsInvalidParams() {
        UpdateTopicDto input = null;

        Throwable thrown = catchThrowable(() -> service.updateTopic(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
    //endregion

    //region delete topic tests
    @Test
    public void deleteTopic_withExistingId_deletesTopic() {
        UUID existingId = repository.save(new Topic("CRUD - part 4", "Basic deletion of topic.")).getId();
        DeleteTopicDto input = new DeleteTopicDto(existingId);

        service.deleteTopic(input);

        assertThat(repository.findAll().iterator().hasNext()).isFalse();
    }

    @Test
    public void deleteTopic_withNotExistingId_throwsNotFound() {
        UUID notExistingId = UUID.randomUUID();
        DeleteTopicDto input = new DeleteTopicDto(notExistingId);

        Throwable thrown = catchThrowable(() -> service.deleteTopic(input));

        assertThat(thrown)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleteTopic_withNotExistingId_throwsInvalidParams() {
        DeleteTopicDto input = null;

        Throwable thrown = catchThrowable(() -> service.deleteTopic(input));

        assertThat(thrown)
                .isInstanceOf(InvalidParamsException.class);
    }
    //endregion
}
