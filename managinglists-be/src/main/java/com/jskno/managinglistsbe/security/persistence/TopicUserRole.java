package com.jskno.managinglistsbe.security.persistence;

import com.jskno.managinglistsbe.domain.Topic;

import javax.persistence.*;

@Entity
@Table(name = "topics_users_roles")
public class TopicUserRole {

    @EmbeddedId
    private TopicUserRoleId id;

    @MapsId("topic_id")
    @ManyToOne
    private Topic topic;

    @MapsId("user_id")
    @ManyToOne
    private User user;

    @MapsId("role_id")
    @ManyToOne
    private Role role;

    public TopicUserRole() {
    }

    public TopicUserRole(Topic topic, User user, Role role) {
        this.topic = topic;
        this.id.setTopicId(topic.getId());

        this.user = user;
        this.id.setUserId(user.getId());

        this.role = role;
        this.id.setRoleId(role.getId());
    }

    public TopicUserRoleId getId() {
        return id;
    }

    public void setId(TopicUserRoleId id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
