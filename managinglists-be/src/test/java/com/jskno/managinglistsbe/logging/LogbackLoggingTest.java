package com.jskno.managinglistsbe.logging;

import com.jskno.managinglistsbe.ManaginglistsBeApplication;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.repositories.TopicRepository;
import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
/*
 *  This test takes the whole configuration from ManaginglistsBeApplication class. So it load the complete normal app config
 *  However the active profile is test so the AuthenticationManager is from test and dev profile
 *  And  DOES load application-test AND logback config files
 *  If we put profile dev will test the dev application properties and would try to initialize the mysql database
 *  It takes by default the application-test properties which set EMBEDED DB and the property format_sql
 *  Prints the log because of loaded file lockback-test.xml and then can print the parameters.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ManaginglistsBeApplication.class,
        })
@ActiveProfiles("test")
public class LogbackLoggingTest {

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
