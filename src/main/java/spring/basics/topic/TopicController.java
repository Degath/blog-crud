package spring.basics.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zales on 14.03.2017.
 */
@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @RequestMapping("/topics")
    public List<Topic> getAllTopics(){
        return topicService.getAllTopics();
    }

    @RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable String id){
        return topicService.getTopic(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics")
    public void addTopic(@RequestBody Topic topic){
        topicService.addTopic(topic);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{id}")
    public void updateTopic(@RequestBody Topic topic, @PathVariable String id){
        topicService.updateTopic(id, topic);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{id}")
    public void deleteTopic(@PathVariable String id){
         topicService.deleteTopic(id);
    }

    //implement custom methods
    @RequestMapping("/topics/search/{name}")
    public Topic getTopicByName(@PathVariable String name) {
        return topicRepository.findById(name);
    }

    @RequestMapping("/topics/search/{id}/{name}")
    public List<Topic> getTopicByNameAndDiscription(@PathVariable String id, @PathVariable String name) {
        return topicRepository.findByIdAndName(id, name);
    }

    //implement custom method with @query annotation
    @RequestMapping("words/querysearch/name/{name}")
    public List<Topic> getTopicByNameQuery(@PathVariable String name) {
        return topicRepository.findByName(name);
    }

    @RequestMapping("words/querysearch/discription/discription}")
    public Topic getTopicByDiscription(@PathVariable String discription) {
        return topicRepository.findByDiscription(discription);
    }
}
