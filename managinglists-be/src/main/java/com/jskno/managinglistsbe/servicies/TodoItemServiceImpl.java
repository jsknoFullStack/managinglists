package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.Attachment;
import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.exception.TodoItemNotFoundException;
import com.jskno.managinglistsbe.repositories.TodoItemRepository;
import com.jskno.managinglistsbe.uploadfile.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemServiceImpl implements TodoItemService{

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public TodoItem saveOrUpdateTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    @Override
    public List<TodoItem> getTodoItemsByTopicName(String topicName) {
        return todoItemRepository.findByTopic_Name(topicName);
    }

    @Override
    public List<TodoItem> getTodoItemsByTopicId(Long topicId) {
        return todoItemRepository.findByTopic_Id(topicId);
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

    @Override
    public TodoItem saveOrUpdateTodoItem(TodoItem todoItem, MultipartFile[] files, Long topicId) {
        for(Attachment attachment : todoItem.getAttachments()) {
            attachment.setId(null);
            attachment.setFilename("12412");
            attachment.setTodoItem(todoItem);
            attachment.setPath("32535");
        }
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        fileStorageService.storeFile(files);
        return savedTodoItem;
    }
}
