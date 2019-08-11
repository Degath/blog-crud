package pl.degath.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.degath.blog.api.dto.TopicResponse;
import pl.degath.blog.infrastucture.QueryHandler;
import pl.degath.blog.topic.TopicDto;
import pl.degath.blog.topic.query.FindTopicByIdQuery;

import java.util.UUID;

@Controller
@RequestMapping("/v1/topics")
public class TopicQueryController {

    private final QueryHandler<FindTopicByIdQuery, TopicDto> handler;

    TopicQueryController(QueryHandler<FindTopicByIdQuery, TopicDto> handler) {
        this.handler = handler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> findTopicById(@PathVariable("id") UUID id) {
        TopicDto topicDto = handler.handle(new FindTopicByIdQuery(id));

        return ResponseEntity.ok(new TopicResponse(topicDto));
    }
}
