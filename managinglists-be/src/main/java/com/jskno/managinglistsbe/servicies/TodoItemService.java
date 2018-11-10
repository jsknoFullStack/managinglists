package com.jskno.managinglistsbe.servicies;


import com.jskno.managinglistsbe.domain.TodoItem;
import com.jskno.managinglistsbe.domain.Topic;

import java.util.List;

public interface TodoItemService {

    TodoItem saveOrUpdateTodoItem(TodoItem todoItem);

    List<TodoItem> getItemsByTopic(Topic topic);
}
