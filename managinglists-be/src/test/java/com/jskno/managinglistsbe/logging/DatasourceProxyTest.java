package com.jskno.managinglistsbe.logging;

import com.jskno.managinglistsbe.config.DatasourceProxyTestConfig;
import com.jskno.managinglistsbe.config.P6SpyTestConfig;
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
 *  This test takes the whole configuration from DatasourceProxyTestConfig class. Does not load application-test nor logback config files
 *  Scan for the domain and repository classes
 *  Prints the log because of the custom creation of datasource wrapped with a datasource proxy and takes their properties from the spy.properties file.
 *  Notice that in this case we save the User and then the Topic with an user nested. This can be done because we add UserRepository package as well in the scan base package
 *  In the HibernateLoggingTest the UserRepository is left out of config.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatasourceProxyTestConfig.class)
public class DatasourceProxyTest {

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
