package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.exception.EntityConstraintViolationException;
import com.jskno.managinglistsbe.exception.TopicNotFoundException;
import com.jskno.managinglistsbe.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic saveOrUpdateTopic(Topic topic) {
        try {
            topic.setName(topic.getName().toUpperCase());
            return topicRepository.save(topic);
        } catch(DataIntegrityViolationException ex) {
            throw new EntityConstraintViolationException("uniqueTopicName",
                    new StringBuilder()
                            .append("This Topic already exist")
                            .append(topic.getName())
                            .toString());
        }
    }

    @Override
    public Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The Topic with id: " + id + " does not exist"));
    }

    @Override
    public Topic findTopicByName(String name) {
        Topic topic = topicRepository.findTopicByName(name.toUpperCase());
        if(topic == null) {
            throw new TopicNotFoundException("Cannot find topic with NAME '" + name.toUpperCase() + "'. This topic does not exist");
        }
        return topic;
    }

    @Override
    public List<Topic> findTopicMatchingName(String name) {
        return topicRepository.findTopicMatchingName(name.toUpperCase());
    }

    @Override
    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void deleteTopicByName(String name) {
        Topic topic = topicRepository.findTopicByName(name.toUpperCase());
        if(topic == null) {
            throw new TopicNotFoundException("Cannot delete topic with NAME '" + name.toUpperCase() + "'. This topic does not exist");
        }
        this.topicRepository.delete(topic);
    }


}
