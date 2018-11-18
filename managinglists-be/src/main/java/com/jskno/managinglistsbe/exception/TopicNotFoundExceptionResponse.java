package com.jskno.managinglistsbe.exception;

public class TopicNotFoundExceptionResponse {

    private String topicName;

    public TopicNotFoundExceptionResponse(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
