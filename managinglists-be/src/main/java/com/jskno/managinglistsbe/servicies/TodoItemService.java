package com.jskno.managinglistsbe.servicies;


import com.jskno.managinglistsbe.domain.TodoItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TodoItemService {

    TodoItem addTodoItem(TodoItem todoItem, Long topicId);

    TodoItem updateTodoItem(TodoItem todoItem, Long topicId, Long todoItemId);

    List<TodoItem> getTodoItemsByTopicName(String topicName);

    List<TodoItem> getTodoItemsByTopicId(Long topicId);

    TodoItem getTodoItemById(Long topicId, Long id);

    void deleteTodoItemById(Long topicId, Long todoItemId);

    TodoItem addTodoItemWithAttachments(TodoItem todoItem, MultipartFile[] files, Long topicId);

    TodoItem updateTodoItemWithAttachments(TodoItem todoItem, MultipartFile[] files, Long id, Long topicId);
}
