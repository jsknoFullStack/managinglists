package com.jskno.managinglistsbe.repositories;

import com.jskno.managinglistsbe.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    @Query("SELECT t FROM TodoItem t JOIN t.topic top WHERE top.id = :topicId")
    public List<TodoItem> getItemsByTopic(@Param("topicId") Long topicId);
}
