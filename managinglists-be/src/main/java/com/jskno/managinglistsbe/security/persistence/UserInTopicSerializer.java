package com.jskno.managinglistsbe.security.persistence;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserInTopicSerializer extends StdSerializer<User> {

    public UserInTopicSerializer() {
        this(null);
    }

    protected UserInTopicSerializer(Class<User> userClass) {
        super(userClass);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeEndObject();

    }
}
