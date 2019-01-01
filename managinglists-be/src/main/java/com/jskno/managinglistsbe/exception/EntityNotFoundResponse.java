package com.jskno.managinglistsbe.exception;

public class EntityNotFoundResponse {

    private String entityName;
    private String message;

    public EntityNotFoundResponse(String entityName, String message) {
        this.entityName = entityName;
        this.message = message;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
