package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.exception.EntityConstraintViolationException;
import com.jskno.managinglistsbe.exception.TopicNotFoundException;
import com.jskno.managinglistsbe.repositories.TopicRepository;
import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Controller
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Topic saveTopic(Topic topic) {
        try {

            User user = userRepository.findByUsername(authenticationService.getAuthenticatedUsername());
            topic.setUser(user);
            normalizeTopicName(topic);
            return topicRepository.save(topic);

        } catch(DataIntegrityViolationException ex) {
            throw new EntityConstraintViolationException("uniqueTopicName",
                    new StringBuilder()
                            .append("This Topic already exists ")
                            .append(topic.getName())
                            .toString());
        }
    }

    @Override
    public Topic updateTopic(Topic topic) {

        Topic existingTopic = this.findTopicById(topic.getId());
//        topic.setUser(existingTopic.getUser());
//        normalizeTopicName(topic);
//        return topicRepository.save(topic);

        existingTopic.setName(topic.getName());
        existingTopic.setDescription(topic.getDescription());
        normalizeTopicName(existingTopic);

        return topicRepository.save(existingTopic);
    }

    private void normalizeTopicName(Topic topic) {
        topic.setName(topic.getName().toUpperCase());
    }

    @Override
    public Topic findTopicById(Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if(!topic.isPresent()) {
            throw new EntityNotFoundException("Topic->The Topic with id: " + id + " does not exist");
        }

        if(!authenticationService.getAuthenticatedUsername().equals(topic.get().getUser().getUsername())) {
            throw new TopicNotFoundException("Topic ID '" + id +
                    "' not found in your account");
        }
        return topic.get();
    }

    @Override
    public void deleteTopicById(Long id) {
        Topic topic = this.findTopicById(id);
        topicRepository.delete(topic);
    }

    @Override
    public List<Topic> findAllTopics() {
        return topicRepository.findAllByUser_Username(
                authenticationService.getAuthenticatedUsername());
    }

    // INI : CODE NOT USED

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
    public void deleteTopicByName(String name) {
        Topic topic = topicRepository.findTopicByName(name.toUpperCase());
        if(topic == null) {
            throw new TopicNotFoundException("Cannot delete topic with NAME '" + name.toUpperCase() + "'. This topic does not exist");
        }
        this.topicRepository.delete(topic);
    }

    // END : CODE NOT USED


}
