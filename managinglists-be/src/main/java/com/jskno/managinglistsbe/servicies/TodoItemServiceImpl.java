package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.Attachment;
import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.exception.TodoItemNotFoundException;
import com.jskno.managinglistsbe.repositories.TodoItemRepository;
import com.jskno.managinglistsbe.uploadfile.FileStorageProperties;
import com.jskno.managinglistsbe.uploadfile.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TodoItemServiceImpl implements TodoItemService{

    @Autowired
    private TopicService topicService;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    FileStorageProperties fileStorageProperties;

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
    public void deleteTodoItemById(Long topicId, Long todoItemId) {
        TodoItem todoItem = this.findTodoItemById(topicId, todoItemId);
        todoItemRepository.delete(todoItem);
    }

    @Override
    @Transactional
    public TodoItem saveTodoItem(TodoItem todoItem, MultipartFile[] files, Long topicId) {
        Topic topic = topicService.findTopicById(topicId);
        String storagePath = fileStorageProperties.getUploadDirectory().concat("\\").concat(topic.getName());
        for(MultipartFile file : files) {
            Attachment attachment = new Attachment();
            attachment.setId(null);
            attachment.setFilename(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setPath(storagePath);

            attachment.setTodoItem(todoItem);
            todoItem.addAttachment(attachment);
        }
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        fileStorageService.storeFiles(topic.getName(), files);
        return savedTodoItem;
    }

    @Override
    @Transactional
    public TodoItem updateTodoItem(TodoItem todoItem, MultipartFile[] files, Long topicId) {
        Topic topic = topicService.findTopicById(topicId);
        String storagePath = fileStorageProperties.getUploadDirectory().concat("\\").concat(topic.getName());
        todoItem.getAttachments().forEach(attachment -> attachment.setTodoItem(todoItem));
        for(MultipartFile file : files) {
            Attachment attachment = new Attachment();
            attachment.setId(null);
            attachment.setFilename(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setPath(storagePath);

            attachment.setTodoItem(todoItem);
            todoItem.addAttachment(attachment);
        }
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        fileStorageService.storeFiles(topic.getName(), files);
        return savedTodoItem;
    }

    private TodoItem findTodoItemById(Long topicId, Long todoItemId) {
        this.topicService.findTopicById(topicId);
        Optional<TodoItem> todoItem = todoItemRepository.findById(todoItemId);
        if(!todoItem.isPresent()) {
            throw new TodoItemNotFoundException(String.format("Cannot delete Todo Item with ID '[%s]'. This item does not exist", todoItemId));
        }
        if(!topicId.equals(todoItem.get().getTopic().getId())) {
            throw new TodoItemNotFoundException(String.format("Todo Item with ID '[%s]', does not exist in Topic with ID '[%s]'.", todoItemId, topicId));
        }
        return todoItem.get();
    }
}
