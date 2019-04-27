package com.jskno.managinglistsbe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jskno.managinglistsbe.domain.base.AbstractEntity;
import com.jskno.managinglistsbe.domain.validations.OnCreateChecks;
import com.jskno.managinglistsbe.domain.validations.OnUpdateChecks;
import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.persistence.UserInTopicSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "username"})})
public class Topic extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = OnCreateChecks.class, message = "The topic id must be null for a new topic")
    @NotNull(groups = OnUpdateChecks.class, message = "The topic id is required to update topic operation")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "The topic name is required")
    @Size(min = 3, max = 45, message = "The topic name must be between 3 and 45 characters")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TodoItem> todoItems;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonSerialize(using = UserInTopicSerializer.class)
    private User user;

    public Topic() {
        this.todoItems = new ArrayList<>();
    }

    public Topic(Long topicId) {
        this.id = topicId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;

        return new EqualsBuilder()
                .append(id, topic.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
