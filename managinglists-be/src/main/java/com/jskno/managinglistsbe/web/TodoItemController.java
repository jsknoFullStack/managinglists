package com.jskno.managinglistsbe.web;

import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.servicies.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("todoitem")
@Validated
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem createNewTodoItem(@Valid @RequestBody TodoItem todoItem) {
        return todoItemService.saveOrUpdateTodoItem(todoItem);
    }

    @GetMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public TodoItem getTodoItemById(@PathVariable Long todoItemId) {
        return todoItemService.getTodoItemById(todoItemId);
    }


    @GetMapping("/todoItemsByTopic")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoItem> getItemsByTopic(@Valid @RequestBody Topic topic) {
        return this.todoItemService.getTodoItemsByTopic(topic);
    }

    @GetMapping("/todoItemsByTopicId/{topicId}")
    public List<TodoItem> getTodoItemsByTopicId(
            @NotNull @Positive(message = "topicId->The topicId must be a positive number")
            @PathVariable Long topicId) {
        return this.todoItemService.getTodoItemsByTopic(new Topic(topicId));
    }

    @DeleteMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTodoItemById(
            @Positive(message = "topicId->The todoItemId must be a positive number")
            @PathVariable Long todoItemId) {
        todoItemService.deleteTodoItemById(todoItemId);
    }



}
