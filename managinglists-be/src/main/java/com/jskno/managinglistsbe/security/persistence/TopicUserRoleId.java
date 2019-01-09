package com.jskno.managinglistsbe.security.persistence;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TopicUserRoleId implements Serializable {

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TopicUserRoleId that = (TopicUserRoleId) o;

        return new EqualsBuilder()
                .append(getTopicId(), that.getTopicId())
                .append(getUserId(), that.getUserId())
                .append(getRoleId(), that.getRoleId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTopicId())
                .append(getUserId())
                .append(getRoleId())
                .toHashCode();
    }
}
