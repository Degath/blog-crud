package pl.degath.blog.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.degath.blog.InMemorySpringRepository;
import pl.degath.blog.infrastucture.QueryHandler;
import pl.degath.blog.port.SpringRepository;
import pl.degath.blog.topic.FindTopicByIdQueryHandler;
import pl.degath.blog.topic.Topic;
import pl.degath.blog.topic.TopicBuilder;
import pl.degath.blog.topic.TopicDto;
import pl.degath.blog.topic.query.FindTopicByIdQuery;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TopicQueryControllerTest {

    private final static String BASE_URL = "/v1/topics";

    private SpringRepository<Topic> repository;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        repository = new InMemorySpringRepository<>();
        QueryHandler<FindTopicByIdQuery, TopicDto> handler = new FindTopicByIdQueryHandler(repository);
        TopicQueryController topicQueryController = new TopicQueryController(handler);
        mockMvc = MockMvcBuilders.standaloneSetup(topicQueryController).build();
    }

    @Test
    void findTopicById_withExistingId_ok() throws Exception {
        UUID existingId = repository.save(new TopicBuilder().build()).getId();

        ResultActions result = mockMvc.perform(get(String.format("%s/%s", BASE_URL, existingId)));

        result.andExpect(status().isOk());
    }

    @Test
    void findTopicById_withNotExistingId_notFound() throws Exception {
        UUID notExistingId = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get(String.format("%s/%s", BASE_URL, notExistingId)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void findTopicById_withInvalidRequest_badRequest() throws Exception {
        UUID invalidId = null;

        ResultActions result = mockMvc.perform(get(String.format("%s/%s", BASE_URL, invalidId)));

        result.andExpect(status().isBadRequest());
    }
}
