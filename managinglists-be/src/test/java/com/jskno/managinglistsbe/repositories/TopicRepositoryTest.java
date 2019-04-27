package com.jskno.managinglistsbe.repositories;

import com.jskno.managinglistsbe.ManaginglistsBeApplication;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ManaginglistsBeApplication.class,
        })
@ActiveProfiles("test")
public class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAndRetrieveTopicTest() {

        Topic topic = generateTopic();
        topicRepository.save(topic);
        Topic retrievedTopic = topicRepository.findTopicByName("Literature");

        Assert.assertNotNull(retrievedTopic);
        Assert.assertEquals(topic.getDescription(), retrievedTopic.getDescription());
    }

    private Topic generateTopic() {
        Topic topic = new Topic();
        topic.setName("Literature");
        topic.setDescription("Fiction books to read");
        topic.setUser(getUser());
        return topic;
    }

    private User getUser() {
        User user = new User();
        user.setUsername("jcano@gmail.com");
        user.setFirstName("Jose");
        user.setLastName("Cano");
        user.setPassword("Password");
        user.setConfirmPassword("Password");

        return userRepository.save(user);
    }
}
