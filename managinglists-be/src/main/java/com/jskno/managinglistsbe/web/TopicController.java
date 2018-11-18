package com.jskno.managinglistsbe.web;

import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.servicies.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("topic")
@Validated
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Topic createNewTopic(@Valid @RequestBody Topic topic) {
        return topicService.saveOrUpdateTopic(topic);
    }

    @GetMapping("/{topicName}")
    @ResponseStatus(HttpStatus.OK)
    public Topic getTopicByName(
            @Size(min = 3, max = 20, message = "topicName->The topic name must be between 3 and 20 characters")
            @PathVariable String topicName) {
        return topicService.findTopicByName(topicName);
    }

    @DeleteMapping("/{topicName}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTopic(
            @Size(min = 3, max = 20,message = "topicName->The topic name must be between 3 and 20 characters")
            @PathVariable String topicName) {
        this.topicService.deleteTopicByName(topicName.toUpperCase());
        return "Topic with NAME: '".concat(topicName.toUpperCase()).concat("' was deleted succesfully.");
    }

    @GetMapping("/all")
    public List<Topic> findAllTopics() {
        return topicService.findAllTopics();
    }

}
