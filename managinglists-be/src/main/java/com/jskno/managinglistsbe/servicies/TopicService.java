package com.jskno.managinglistsbe.servicies;


import com.jskno.managinglistsbe.domain.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> findAllTopics();
    Topic findTopicById(Long id);
    Topic findTopicByName(String name);
    List<Topic> findTopicMatchingName(String name);
    void deleteTopicById(Long id);
    void deleteTopicByName(String name);
    Topic saveOrUpdateTopic(Topic topic);
}
