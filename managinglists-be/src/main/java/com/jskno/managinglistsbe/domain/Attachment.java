package com.jskno.managinglistsbe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "filename")
    @NotBlank(message = "The filename is required")
    private String filename;

    @Column(name = "size")
    private Long size;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "todoitem_id")
    @NotNull(message = "The attachment must be linked to a TodoItem")
    @JsonIgnore
    private TodoItem todoItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItem todoItem) {
        this.todoItem = todoItem;
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
