package com.jskno.managinglistsbe.domain;


import com.jskno.managinglistsbe.domain.base.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attachments")
public class Attachment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "todoitem_id")
    @NotNull(message = "The attachment must be linked to a TodoItem")
    private TodoItem todoItem;

    @Column(name = "path")
    @NotBlank(message = "The attachment path is required")
    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItem todoItem) {
        this.todoItem = todoItem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", todoItem=" + todoItem +
                ", path='" + path + '\'' +
                '}';
    }
}
