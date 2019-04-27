package com.jskno.managinglistsbe.logging;

import com.jskno.managinglistsbe.config.HibernateLoggingTestConfig;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.repositories.TopicRepository;
import com.jskno.managinglistsbe.security.persistence.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

/*
*  This test takes the whole configuration from H2TestProfileJPAConfig class. Does not load application-test nor logback config files
*  Prints the log because of the custom creation of datasource and entity manager that takes their properties set from test.properties file.
*  It does not print parameters because we don't use here the logback-test.xml file
*  Notice that in this case we save don't save an User nested in a Topic. This cannot be done because the UserRepository repository package is not in the scan base package
*
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HibernateLoggingTestConfig.class)
public class HibernateLoggingTest {

    @Autowired
    private TopicRepository topicRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Test
    public void saveAndRetrieveTopicTest() {

        Topic topic = generateTopic();
        topicRepository.save(topic);
        Topic retrievedTopic = topicRepository.findTopicByName("Literature");

        Assert.assertNotNull(retrievedTopic);
        Assert.assertEquals(topic.getDescription(), retrievedTopic.getDescription());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void saveTopicWithUnsavedUserTest() {

        Topic topic = generateTopicWithUser();
        topicRepository.save(topic);
    }

    private Topic generateTopic() {
        Topic topic = new Topic();
        topic.setName("Literature");
        topic.setDescription("Fiction books to read");
        return topic;
    }

    private Topic generateTopicWithUser() {
        Topic topic = generateTopic();
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

        return user;
    }

}
