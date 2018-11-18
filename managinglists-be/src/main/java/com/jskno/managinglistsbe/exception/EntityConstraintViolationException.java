package com.jskno.managinglistsbe.exception;

public class EntityConstraintViolationException extends RuntimeException {

    private String constraintRule;

    public EntityConstraintViolationException(String message, String constraintRule) {
        super(message);
        this.constraintRule = constraintRule;
    }

    public String getConstraintRule() {
        return constraintRule;
    }
}
