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
import java.sql.Timestamp;
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
        log.debug("EmptyDaysDeserializer.deserialize ::::");
        JsonNode node = parser.getCodec().readTree(parser);
        String emptyDateStr = node.get("emptydate").asText();
        long userIdFK = node.get("userIdFk").asLong();
        String familyIdFK = node.get("familyIdFk").asText();
        Date emptydate = null;
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(emptyDateStr);
            emptydate = new Date(utilDate.getTime());
            log.error(emptydate.toString());
        }
        catch (Exception e) {
            throw new IOException(e);
        }
        EmptyDays emptyDays = new EmptyDays(familyIdFK,userIdFK,emptydate);
        return emptyDays;
    }
}
