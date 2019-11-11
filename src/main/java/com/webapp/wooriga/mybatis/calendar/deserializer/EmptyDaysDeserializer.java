package com.webapp.wooriga.mybatis.calendar.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class EmptyDaysDeserializer extends StdDeserializer<EmptyDays> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDAO userDAO;
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
        User user = userDAO.selectOne(userIdFK);
        String familyId = node.get("familyId").asText();
        Date emptydate = null;
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(emptyDateStr);
            emptydate = new Date(utilDate.getTime());
        }
        catch (Exception e) {
            throw new IOException(e);
        }
        EmptyDays emptyDays = new EmptyDays(familyId,userIdFK,emptydate);
        return emptyDays;
    }
}
