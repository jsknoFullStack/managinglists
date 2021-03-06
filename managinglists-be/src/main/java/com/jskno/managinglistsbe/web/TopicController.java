package com.jskno.managinglistsbe.web;

import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.domain.validations.OnCreateChecks;
import com.jskno.managinglistsbe.domain.validations.OnUpdateChecks;
import com.jskno.managinglistsbe.servicies.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("topic")
@Validated
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Topic createNewTopic(@Validated(OnCreateChecks.class) @RequestBody Topic topic) {
        return topicService.saveTopic(topic);
    }

    @PatchMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Topic updateTopic(@Validated(OnUpdateChecks.class) @RequestBody Topic topic) {
        return topicService.updateTopic(topic);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Topic getTopicById(
            @Positive(message = "topicId->The topic id must be a positive number")
            @PathVariable Long id) {
        return topicService.findTopicById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTopicById(
            @Positive(message = "topicId->The topic id must be a positive number")
            @PathVariable Long id) {
        this.topicService.deleteTopicById(id);
        return "Topic with ID: '".concat(String.valueOf(id)).concat("' was deleted succesfully.");
    }

    @GetMapping("/all")
    public List<Topic> findAllTopics() {
        return topicService.findAllTopics();
    }

    // INI : CODE NOT USED

    /*
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Topic getTopicByName(
            @Size(min = 3, max = 45, message = "name->The topic name must be between 3 and 45 characters")
            @PathVariable String name) {
        return topicService.findTopicByName(name);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTopic(
            @Size(min = 3, max = 45,message = "name->The topic name must be between 3 and 45 characters")
            @PathVariable String name) {
        this.topicService.deleteTopicByName(name);
        return "Topic with NAME: '".concat(name.toUpperCase()).concat("' was deleted succesfully.");
    }
    */

    // END : CODE NOT USED

}
