package com.jskno.managinglistsbe.servicies;


import com.jskno.managinglistsbe.domain.TodoItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TodoItemService {

    TodoItem saveOrUpdateTodoItem(TodoItem todoItem);

    List<TodoItem> getTodoItemsByTopicName(String topicName);

    List<TodoItem> getTodoItemsByTopicId(Long topicId);

    TodoItem getTodoItemById(Long id);

    void deleteTodoItemById(Long topicId, Long todoItemId);

    TodoItem saveTodoItem(TodoItem todoItem, MultipartFile[] files, Long topicId);

    TodoItem updateTodoItem(TodoItem todoItem, MultipartFile[] files, Long topicId);
}
