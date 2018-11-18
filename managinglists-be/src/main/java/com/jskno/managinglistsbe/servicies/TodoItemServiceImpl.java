package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.exception.TodoItemNotFoundException;
import com.jskno.managinglistsbe.repositories.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemServiceImpl implements TodoItemService{

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Override
    public TodoItem saveOrUpdateTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    @Override
    public List<TodoItem> getTodoItemsByTopic(Topic topic) {
        return todoItemRepository.getItemsByTopic(topic.getId());
    }

    @Override
    public TodoItem getTodoItemById(Long id) {
        Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        return todoItem.orElseThrow(
                () -> new TodoItemNotFoundException(String.format("Cannot find Todo Item with ID '[%s]'. This item does not exist", id)));
    }

    @Override
    public void deleteTodoItemById(Long id) {
        Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        if(todoItem.isPresent()) {
            throw new TodoItemNotFoundException(String.format("Cannot delete Todo Item with ID '[%s]'. This item does not exist", id));
        }
        todoItemRepository.delete(todoItem.get());
    }
}
