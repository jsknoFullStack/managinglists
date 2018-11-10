package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.repositories.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService{

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Override
    public TodoItem saveOrUpdateTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    @Override
    public List<TodoItem> getItemsByTopic(Topic topic) {
        return todoItemRepository.getItemsByTopic(topic.getId());
    }
}
