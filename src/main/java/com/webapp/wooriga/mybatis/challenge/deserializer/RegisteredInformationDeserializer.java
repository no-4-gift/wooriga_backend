package com.webapp.wooriga.mybatis.challenge.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.challenge.result.RegisteredInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class RegisteredInformationDeserializer extends StdDeserializer<RegisteredInformation> {

    Logger log = LoggerFactory.getLogger(this.getClass());

   public RegisteredInformationDeserializer() {
        this(null);
    }

    public RegisteredInformationDeserializer(Class<RegisteredInformation> t) {
        super(t);
    }

    @Override
    public RegisteredInformation deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        log.debug("RegisteredInformationDeserializer.deserialize ::::");
        JsonNode node = parser.getCodec().readTree(parser);

        long chiefIdFK = node.get("chiefIdFK").asLong();
        int challengeIdFK = node.get("challengeIdFK").asInt();
        String resolution = node.get("resolution").asText();
        String familyId = node.get("familyId").asText();
        RegisteredChallenges registeredChallenges = RegisteredChallenges.builder()
                .challengeIdFK(challengeIdFK)
                .chiefIdFK(chiefIdFK)
                .familyId(familyId)
                .resolution(resolution)
                .build();
        JsonNode participant = node.get("participantFK");
        Participants[] participants = new Participants[participant.size()];
        int i = 0;
        for(JsonNode objNode : participant){
            participants[i]= Participants.builder()
                    .participantFK(objNode.asLong())
                    .build();
            i++;
        }

        JsonNode registeredDate = node.get("registeredDate");
        Certifications[] certifications = new Certifications[registeredDate.size()];
        i = 0;
        for(JsonNode objNode : registeredDate){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try{
                date = new Date(simpleDateFormat.parse(objNode.asText()).getTime());
            }
            catch(Exception e){
                log.error(e.toString());
                throw new IOException(e);
            }
            certifications[i] = Certifications.builder()
                    .registeredDate(date)
                    .build();
            i++;
        }
        return RegisteredInformation.builder()
                .certifications(certifications)
                .participants(participants)
                .registeredChallenges(registeredChallenges)
                .build();
    }

}
