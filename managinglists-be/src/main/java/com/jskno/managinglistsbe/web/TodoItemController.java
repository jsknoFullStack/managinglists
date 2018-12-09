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
@RequestMapping("topic/{topicId}/todoitem")
@Validated
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem createNewTodoItem(@Valid @RequestBody TodoItem todoItem, @PathVariable String topicId) {
        return todoItemService.saveOrUpdateTodoItem(todoItem);
    }

    @PatchMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public TodoItem updateTodoItem(@Valid @RequestBody TodoItem todoItem, @PathVariable String topicId, @PathVariable Long todoItemId) {
        return todoItemService.saveOrUpdateTodoItem(todoItem);
    }

    @GetMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public TodoItem getTodoItemById(@PathVariable String topicId, @PathVariable Long todoItemId) {
        return todoItemService.getTodoItemById(todoItemId);
    }


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoItem> getTodoItemsByTopicName(@PathVariable Long topicId) {
        return this.todoItemService.getTodoItemsByTopicId(topicId);
    }

    @DeleteMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTodoItemById(
            @PathVariable Long topicI,
            @Positive(message = "todoItemId->The todoItemId must be a positive number")
            @PathVariable Long todoItemId) {
        todoItemService.deleteTodoItemById(todoItemId);
    }



}
