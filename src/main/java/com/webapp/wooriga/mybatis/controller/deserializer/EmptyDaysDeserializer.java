package com.webapp.wooriga.mybatis.controller.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class EmptyDaysDeserializer extends StdDeserializer<EmptyDays> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    public EmptyDaysDeserializer() {
        this(null);
    }

    public EmptyDaysDeserializer(Class<EmptyDays> t) {
        super(t);
    }

    @Override
    public EmptyDays deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException{
        log.debug("UsersDeserializer.deserialize ::::");
        JsonNode node = parser.getCodec().readTree(parser);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        EmptyDays emptyDays = new EmptyDays();
        String emptyDateStr = node.get("emptydate").asText();
        String userIdFK = node.get("userIdFk").asText();
        Long familyIdFK = node.get("familyIdFk").asLong();
        Date emptyDate = null;
        try {
            emptyDate = new Date(format.parse(emptyDateStr).getTime());
        }
        catch (Exception e) {
            throw new IOException(e);
        }
        emptyDays.setEmptydate(emptyDate);
        emptyDays.setUserIdFk(userIdFK);
        emptyDays.setFamilyIdFk(familyIdFK);
        return emptyDays;
    }
}
