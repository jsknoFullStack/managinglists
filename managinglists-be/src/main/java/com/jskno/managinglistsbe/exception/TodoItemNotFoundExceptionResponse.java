package com.jskno.managinglistsbe.exception;

public class TodoItemNotFoundExceptionResponse {

    private String todoItemId;

    public TodoItemNotFoundExceptionResponse(String todoItemId) {
        this.todoItemId = todoItemId;
    }

    public String getTodoItemId() {
        return todoItemId;
    }

    public void setTodoItemId(String todoItemId) {
        this.todoItemId = todoItemId;
    }
}
