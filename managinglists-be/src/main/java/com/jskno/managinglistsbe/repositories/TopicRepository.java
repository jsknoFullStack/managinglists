package com.jskno.managinglistsbe.repositories;

import com.jskno.managinglistsbe.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t WHERE t.name = :name")
    Topic findTopicByName(String name);

    Topic findByName(String name);

    //@Query("SELECT t FROM Topic t WHERE t.name LIKE '%' || :name")
    @Query("SELECT t FROM Topic t WHERE t.name LIKE CONCAT('%',:name,'%')")
    List<Topic> findTopicMatchingName(String name);

    List<Topic> findAllByUser_Username(String username);

    @Query("SELECT DISTINCT t FROM Topic t JOIN FETCH t.todoItems ti WHERE t.name LIKE CONCAT('%',:name,'%')")
//    @Query("SELECT t FROM Topic t JOIN t.todoItems ti WHERE t.name LIKE CONCAT('%',:name,'%')")
    List<Topic> findTopicAndITemsMatchingName(String name);

 }
