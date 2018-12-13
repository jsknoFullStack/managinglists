package com.jskno.managinglistsbe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jskno.managinglistsbe.domain.base.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "todoitems")
public class TodoItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @NotNull(message = "Every TodoItem must be linked to a Topic")
//    @JsonIgnore
    private Topic topic;

    @Column(name = "element")
    @NotBlank(message = "The TodoItem element is required")
    private String element;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "todoItem", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", topic=" + topic +
                ", element='" + element + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
