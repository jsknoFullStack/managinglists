package com.jskno.managinglistsbe.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.io.IOException;

public class TopicInTodoItemSerializer extends StdSerializer<Topic> {

    public TopicInTodoItemSerializer() {
        this(null);
    }

    protected TopicInTodoItemSerializer(Class<Topic> topicClass) {
        super(topicClass);
    }

    @Override
    public void serialize(Topic topic, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", topic.getId());
        jsonGenerator.writeStringField("name", topic.getName());
        jsonGenerator.writeEndObject();

    }
}
