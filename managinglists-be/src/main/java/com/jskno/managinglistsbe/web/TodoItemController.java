package com.jskno.managinglistsbe.web;

import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;
import com.jskno.managinglistsbe.servicies.TodoItemService;
import com.jskno.managinglistsbe.servicies.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("todoitem")
public class TodoItemController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/getTodoItemsByTopic")
    public List<TodoItem> getItemsByTopic(@RequestBody Topic topic) {
        return this.todoItemService.getItemsByTopic(topic);
    }

    @GetMapping("/getTodoItemsByTopicId")
    public List<TodoItem> getItemsByTopicId(@RequestParam Long topicId) {
        return this.todoItemService.getItemsByTopic(new Topic(topicId));
    }

    @PostMapping("")
    public TodoItem createNewTodoItem(@Valid @RequestBody TodoItem todoItem, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getErrorCount());
        }
        TodoItem savedItem = todoItemService.saveOrUpdateTodoItem(todoItem);
        System.out.println(savedItem);
        return savedItem;
    }

    public void deleteTopic(Topic topic) {
        topicService.deleteTopicById(topic.getId());
    }

}
