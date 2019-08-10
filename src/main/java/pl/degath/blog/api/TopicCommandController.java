package pl.degath.blog.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.degath.blog.api.dto.TopicRequest;
import pl.degath.blog.infrastucture.CommandHandler;
import pl.degath.blog.infrastucture.CreateCommandHandler;
import pl.degath.blog.topic.command.CreateTopicCommand;
import pl.degath.blog.topic.command.DeleteTopicCommand;
import pl.degath.blog.topic.command.UpdateTopicCommand;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Controller
@RequestMapping("/v1/topics")
public class TopicCommandController {

    private final CreateCommandHandler<CreateTopicCommand> createHandler;
    private final CommandHandler<UpdateTopicCommand> updateHandler;
    private final CommandHandler<DeleteTopicCommand> deleteHandler;

    TopicCommandController(CreateCommandHandler<CreateTopicCommand> createHandler, CommandHandler<UpdateTopicCommand> updateHandler, CommandHandler<DeleteTopicCommand> deleteHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
    }

    @PostMapping
    public ResponseEntity<Void> createTopic(@RequestBody TopicRequest request) throws URISyntaxException {
        CreateTopicCommand command = new CreateTopicCommand(request.getTopicName(), request.getTopicDescription());
        UUID id = createHandler.handle(command);
        URI createdTopicLocation = new URI("/v1/topics/" + id);
        return ResponseEntity.created(createdTopicLocation).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTopic(@PathVariable("id") UUID topicId,
                                            @RequestBody TopicRequest request) {
        UpdateTopicCommand command = new UpdateTopicCommand(topicId, request.getTopicName(), request.getTopicDescription());
        updateHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("id") UUID topicId) {
        DeleteTopicCommand command = new DeleteTopicCommand(topicId);
        deleteHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}
