package com.jskno.managinglistsbe.web;

import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.servicies.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("topic/{topicId}/todoitem")
@Validated
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem addTodoItemToTopic(@Valid @RequestBody TodoItem todoItem, @PathVariable Long topicId) {
        return todoItemService.addTodoItem(todoItem, topicId);
    }

    @PatchMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public TodoItem updateTodoItem(@Valid @RequestBody TodoItem todoItem, @PathVariable Long topicId, @PathVariable Long todoItemId) {
        return todoItemService.updateTodoItem(todoItem, topicId, todoItemId);
    }

    @PostMapping(value = "/attachments", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem addTodoItemToTopicWithAttachements(@Valid @RequestPart("todoItem") TodoItem todoItem,
                                                     @RequestPart("files") MultipartFile[] files,
                                                     @PathVariable Long topicId) {
        return todoItemService.addTodoItemWithAttachments(todoItem, files, topicId);
    }

    @PatchMapping(value="{todoItemId}/attachments", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public TodoItem updateTodoItemWithAttachments(@Valid @RequestPart TodoItem todoItem,
                                                  @RequestPart("files") MultipartFile[] files,
                                                  @PathVariable Long topicId,
                                                  @PathVariable Long todoItemId) {
        return todoItemService.updateTodoItemWithAttachments(todoItem, files, topicId, todoItemId);
    }

    @GetMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public TodoItem getTodoItemById(@PathVariable Long topicId, @PathVariable Long todoItemId) {
        return todoItemService.getTodoItemById(topicId, todoItemId);
    }


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoItem> getTodoItemsByTopicName(@PathVariable Long topicId) {
        return this.todoItemService.getTodoItemsByTopicId(topicId);
    }

    @DeleteMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTodoItemById(
            @PathVariable Long topicId,
            @Positive(message = "todoItemId->The todoItemId must be a positive number")
            @PathVariable Long todoItemId) {
        todoItemService.deleteTodoItemById(topicId, todoItemId);
        return "TodItem with ID: '".concat(String.valueOf(todoItemId)).concat("' was deleted succesfully.");

    }

}
