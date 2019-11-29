package com.webapp.wooriga.mybatis.auth.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webapp.wooriga.mybatis.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<User> t) {
        super(t);
    }

    @Override
    public User deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        log.debug("UserDeserializer.deserialize ::::");
        JsonNode node = parser.getCodec().readTree(parser);
        long id = node.get("id").asLong();
        String nickname = node.get("nickname").asText();
        String profile = node.get("profile").asText();

        User user = new User(id,nickname,profile);
        return user;
    }
}

