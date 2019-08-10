package pl.degath.blog.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.degath.blog.InMemorySpringRepository;
import pl.degath.blog.infrastucture.CommandHandler;
import pl.degath.blog.infrastucture.CreateCommandHandler;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.*;
import pl.degath.blog.topic.command.CreateTopicCommand;
import pl.degath.blog.topic.command.DeleteTopicCommand;
import pl.degath.blog.topic.command.UpdateTopicCommand;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TopicCommandControllerTest {

    private final static String BASE_URL = "/v1/topics";

    private SpringRepository<Topic> repository;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository = new InMemorySpringRepository<>();
        CreateCommandHandler<CreateTopicCommand> createHandler = new CreateTopicCommandHandler(repository);
        CommandHandler<UpdateTopicCommand> updateHandler = new UpdateTopicCommandHandler(repository);
        CommandHandler<DeleteTopicCommand> deleteHandler = new DeleteTopicCommandHandler(repository);
        TopicCommandController topicCommandController = new TopicCommandController(createHandler, updateHandler, deleteHandler);
        mockMvc = MockMvcBuilders.standaloneSetup(topicCommandController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createTopic_withValidRequest_created201() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new TopicRequestBuilder().build());

        ResultActions result = mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody));

        result.andExpect(status().isCreated());
    }

    @Test
    void createTopic_withInvalidRequest_badRequest400() throws Exception {
        String requestBody = "null";

        ResultActions result = mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void updateTopic_onExistingTopicWithValidBody_responseOk() throws Exception {
        UUID existingId = repository.save(new TopicBuilder().build()).getId();
        String requestBody = objectMapper.writeValueAsString(new TopicRequestBuilder().withTopicName("Java basics.").build());

        ResultActions result = mockMvc.perform(patch(String.format("%s/%s", BASE_URL, existingId))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody));

        result.andExpect(status().isOk());
    }

    @Test
    void updateTopic_onNotExistingTopic_responseNotFound() throws Exception {
        UUID notExistingId = UUID.randomUUID();
        String requestBody = objectMapper.writeValueAsString(new TopicRequestBuilder().withTopicName("Java basics.").build());

        ResultActions result = mockMvc.perform(patch(String.format("%s/%s", BASE_URL, notExistingId))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody));

        result.andExpect(status().isNotFound());
    }

    @Test
    void updateTopic_onExistingTopicWithInvalidBody_responseBadRequest() throws Exception {
        UUID notExistingId = repository.save(new TopicBuilder().build()).getId();
        String requestBody = "null";

        ResultActions result = mockMvc.perform(patch(String.format("%s/%s", BASE_URL, notExistingId))
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void deleteTopic_onExistingTopic_responseNoContent() throws Exception {
        UUID existingId = repository.save(new TopicBuilder().build()).getId();

        ResultActions result = mockMvc.perform(delete(String.format("%s/%s", BASE_URL, existingId)));

        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteTopic_onNotExistingTopic_responseNotFound() throws Exception {
        UUID notExistingId = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete(String.format("%s/%s", BASE_URL, notExistingId)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteTopic_withInvalidRequest_responseBadRequest() throws Exception {
        UUID notExistingId = null;

        ResultActions result = mockMvc.perform(delete(String.format("%s/%s", BASE_URL, notExistingId)));

        result.andExpect(status().isBadRequest());
    }
}
