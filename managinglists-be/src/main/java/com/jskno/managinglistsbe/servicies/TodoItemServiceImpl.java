package com.jskno.managinglistsbe.servicies;

import com.jskno.managinglistsbe.domain.Attachment;
import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.exception.TodoItemNotFoundException;
import com.jskno.managinglistsbe.repositories.TodoItemRepository;
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

    @Override
    public TodoItem addTodoItem(TodoItem todoItem, Long topicId) {
        Topic topic = this.topicService.findTopicById(topicId);
        this.checkConsistency(topic, todoItem);
        //todoItem.setTopic(topic);
        return todoItemRepository.save(todoItem);
    }

    @Override
    public TodoItem updateTodoItem(TodoItem todoItem, Long topicId, Long todoItemId) {
        TodoItem existingTodoItem = this.findTodoItemById(topicId, todoItemId);
        this.checkConsistency(existingTodoItem, todoItem);
        //todoItem.setTopic(topic);
        return todoItemRepository.save(todoItem);
    }

    private void checkConsistency(Topic topic, TodoItem todoItem) {
        if(!topic.getId().equals(todoItem.getTopic().getId())) {
            throw new TodoItemNotFoundException(String.format("Topic with ID '[%s]', does not match ID sent in body of todoItem '[%s]'.",
                    topic.getId(), todoItem.getTopic().getId()));
        }
    }

    @Override
    public List<TodoItem> getTodoItemsByTopicName(String topicName) {
        return todoItemRepository.findByTopic_Name(topicName);
    }

    @Override
    public List<TodoItem> getTodoItemsByTopicId(Long topicId) {
        this.topicService.findTopicById(topicId);
        return todoItemRepository.findByTopic_Id(topicId);
    }

    @Override
    public TodoItem getTodoItemById(Long topicId, Long todoItemId) {
        return this.findTodoItemById(topicId, todoItemId);
    }

    @Override
    public void deleteTodoItemById(Long topicId, Long todoItemId) {
        TodoItem todoItem = this.findTodoItemById(topicId, todoItemId);
        todoItemRepository.delete(todoItem);
    }

    @Override
    @Transactional
    public TodoItem addTodoItemWithAttachments(TodoItem todoItem, MultipartFile[] files, Long topicId) {
        Topic topic = topicService.findTopicById(topicId);
        this.checkConsistency(topic, todoItem);
        this.processNewAttachments(topic.getName(), files, todoItem);
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        fileStorageService.storeFiles(topic.getName(), files);
        return savedTodoItem;
    }

    @Override
    @Transactional
    public TodoItem updateTodoItemWithAttachments(TodoItem todoItem, MultipartFile[] files, Long topicId, Long todoItemId) {
        TodoItem existingTodoItem = this.findTodoItemById(topicId, todoItemId);
        this.checkConsistency(existingTodoItem, todoItem);
        todoItem.getAttachments().forEach(attachment -> attachment.setTodoItem(todoItem));
        this.processNewAttachments(existingTodoItem.getTopic().getName(), files, todoItem);
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        fileStorageService.storeFiles(existingTodoItem.getTopic().getName(), files);
        return savedTodoItem;
    }

    private TodoItem findTodoItemById(Long topicId, Long todoItemId) {
        this.topicService.findTopicById(topicId);
        Optional<TodoItem> todoItem = todoItemRepository.findById(todoItemId);
        if(!todoItem.isPresent()) {
            throw new TodoItemNotFoundException(String.format("Todo Item with ID '[%s]' does not exist", todoItemId));
        }
        if(!topicId.equals(todoItem.get().getTopic().getId())) {
            throw new TodoItemNotFoundException(String.format("Todo Item with ID '[%s]', does not exist in Topic with ID '[%s]'.", todoItemId, topicId));
        }
        return todoItem.get();
    }

    private void checkConsistency(TodoItem existingTodoItem, TodoItem todoItem) {
        checkConsistency(existingTodoItem.getTopic(), todoItem);
        if(!existingTodoItem.getId().equals(todoItem.getId())) {
            throw new TodoItemNotFoundException(String.format("Todo Item with ID '[%s]', does not match ID sent in body '[%s]'.",
                    existingTodoItem.getId(), todoItem.getId()));
        }
    }

    private void processNewAttachments(String topicName, MultipartFile[] files, TodoItem todoItem) {
        String storagePath = fileStorageService.getStoragePath(topicName);
        for(MultipartFile file : files) {
            Attachment attachment = new Attachment();
            attachment.setId(null);
            attachment.setFilename(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setPath(storagePath);

            attachment.setTodoItem(todoItem);
            todoItem.addAttachment(attachment);
        }
    }

}
