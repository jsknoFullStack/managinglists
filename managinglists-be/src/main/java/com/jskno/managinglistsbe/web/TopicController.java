package com.jskno.managinglistsbe.web;

import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.servicies.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/showList")
    public List<Topic> findAllTopics() {
        return topicService.findAllTopics();
    }

    @PostMapping("")
    public Topic createNewTopic(@Valid @RequestBody Topic topic, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        Topic savedTopic = topicService.saveOrUpdateTopic(topic);
        System.out.println(savedTopic);
        return savedTopic;
    }

    public void deleteTopic(Topic topic) {
        topicService.deleteTopicById(topic.getId());
    }

}
