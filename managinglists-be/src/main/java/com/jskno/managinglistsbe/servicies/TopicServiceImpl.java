package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The Topic with id: " + id + " does not exist"));
    }

    @Override
    public Topic findTopicByName(String name) {
        return topicRepository.findTopicByName(name);
    }

    @Override
    public List<Topic> findTopicMatchingName(String name) {
        return topicRepository.findTopicMatchingName(name);
    }

    @Override
    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public Topic saveOrUpdateTopic(Topic topic) {
        return topicRepository.save(topic);
    }
}
