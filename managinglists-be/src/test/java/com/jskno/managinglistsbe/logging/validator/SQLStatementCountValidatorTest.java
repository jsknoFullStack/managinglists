package com.jskno.managinglistsbe.logging.validator;

import com.jskno.managinglistsbe.config.DatasourceProxyTestConfig;
import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.logging.validator.sql.SQLStatementCountValidator;
import com.jskno.managinglistsbe.repositories.TopicRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatasourceProxyTestConfig.class)
public class SQLStatementCountValidatorTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private EntityManagerFactory emf;

    // We first launch the test switching between lazy and eager fetching policy to observe
    // how the queries executed increasing when we want to load the TodoItems
    @Test
    public void testNPlusOne() {

        List<Topic> topics = generateTopics();
        topics.forEach(topic -> topicRepository.save(topic));

        LOGGER.info( "Detect N+1" );
        SQLStatementCountValidator.reset();
        List<Topic> retrievedTopics = topicRepository.findTopicMatchingName("Literature");

        Assert.assertNotNull(retrievedTopics);
        assertEquals( 1, retrievedTopics.size() );
        //retrievedTopics.forEach(topic -> assertNull(topic.getTodoItems()));
        SQLStatementCountValidator.assertSelectCount( 1 );


        /*
        With the fetch policy set to EAGER and be able to retrieved the todoITems the number of queries would be 3 instead of one
        @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
        @JsonIgnore
        private List<TodoItem> todoItems;
        SQLStatementCountValidator.assertSelectCount( 2 );
         */
//        retrievedTopics.forEach(topic -> assertNotNull(topic.getTodoItems()));
//        SQLStatementCountValidator.assertSelectCount( 2 );


    }

    // Then in order to avoid launching 2 queries to load the Topics with their TodoItems fetched, we build the query with the JOIN FETCH
    // In this case the entity will be loaded correctly with a single query.
    @Test
    public void testJoinFetch() {

        List<Topic> topics = generateTopics();
        topics.forEach(topic -> topicRepository.save(topic));

        LOGGER.info("Join fetch to prevent N+1. Counter to zero");
        SQLStatementCountValidator.reset();

        List<Topic> retrievedTopics = topicRepository.findTopicAndITemsMatchingName("Literature");
//        List<Topic> retrievedTopics = emf.createEntityManager()
//                .createQuery("select distinct t from Topic t join fetch t.todoItems", Topic.class)
//                .getResultList();

        Assert.assertNotNull(retrievedTopics);
        assertEquals( 1, retrievedTopics.size() );
        Assert.assertNotNull(retrievedTopics.get(0).getTodoItems());
        assertEquals( 2, retrievedTopics.get(0).getTodoItems().size() );

        for(TodoItem todoItem : retrievedTopics.get(0).getTodoItems()) {
            assertNotNull(todoItem.getId());
            assertNotNull(todoItem.getTopic());
            assertNotNull(todoItem.getTopic().getTodoItems());
            assertEquals(2, todoItem.getTopic().getTodoItems().size());
        }

            SQLStatementCountValidator.assertSelectCount(1);
    }

//    @Test
//    public void testNoBatch() {
//
//        List<Topic> topics = generateTopics();
//        SQLStatementCountValidator.reset();
//        LOGGER.info("Insert counter when we save several entities. Counter to zero");
//        topics.forEach(topic -> topicRepository.save(topic));
//        SQLStatementCountValidator.assertInsertCount(4);
//
//    }
//
//    @Test
//    public void testBatch() {
//
//        //emf.getProperties().put("hibernate.jdbc.batch_size", "5");
//        List<Topic> topics = generateTopics();
//        SQLStatementCountValidator.reset();
//        LOGGER.info("Insert counter when we save several entities activating BATCH. Counter to zero");
//        topics.forEach(topic -> topicRepository.save(topic));
//        SQLStatementCountValidator.assertInsertCount(2);
//
//    }

    private List<Topic> generateTopics() {
        List<Topic> topics = new ArrayList<>();

        Topic topic1 = new Topic();
        topic1.setName("Literature");
        topic1.setDescription("Fiction books to read");
        topic1.setTodoItems(getTodoItems(topic1));

        Topic topic2 = new Topic();
        topic2.setName("Economics");
        topic2.setDescription("Non fiction books to read about economy");

        topics.add(topic1);
        topics.add(topic2);
        return topics;
    }

    private List<TodoItem> getTodoItems(Topic topic) {

        List<TodoItem> todoItems = new ArrayList<>();

        TodoItem todoItem1 = new TodoItem();
        todoItem1.setElement("Clean");
        todoItem1.setTopic(topic);

        TodoItem todoItem2 = new TodoItem();
        todoItem2.setElement("Organize");
        todoItem2.setTopic(topic);

        todoItems.add(todoItem1);
        todoItems.add(todoItem2);
        return todoItems;
    }
}
