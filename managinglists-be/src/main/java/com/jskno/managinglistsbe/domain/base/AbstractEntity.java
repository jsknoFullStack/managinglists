package com.jskno.managinglistsbe.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jskno.managinglistsbe.domain.listener.EntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(EntityListener.class)
public class AbstractEntity implements Serializable {

    @Column(name = "CREATED_BY_USER")
    @JsonIgnore
    private String createdByUser;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createdAt;

    @Column(name = "UPDATED_BY_USER")
    @JsonIgnore
    private String updatedByUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    @JsonIgnore
    private Date updatedAt;

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
