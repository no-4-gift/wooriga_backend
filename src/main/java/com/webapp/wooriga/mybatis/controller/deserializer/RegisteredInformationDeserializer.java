package com.webapp.wooriga.mybatis.controller.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import com.webapp.wooriga.mybatis.vo.RegisteredDates;
import com.webapp.wooriga.mybatis.vo.RegisteredInformation;
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
        RegisteredChallenges registeredChallenges = new RegisteredChallenges();

        String chiefIdFK = node.get("chiefIdFK").asText();
        long challengeIdFK = node.get("challengeIdFK").asLong();
        String resolution = node.get("resolution").asText();
        long familyIdFK = node.get("familyIdFK").asLong();
        registeredChallenges.setChallengeIdFK(challengeIdFK);
        registeredChallenges.setChiefIdFK(chiefIdFK);
        registeredChallenges.setFamilyIdFK(familyIdFK);
        registeredChallenges.setResolution(resolution);

        JsonNode participant = node.get("participantFK");
        Participants[] participants = new Participants[participant.size()];
        for(int i = 0; i < participants.length; i++)
            participants[i] = new Participants();
        int i = 0;
        for(JsonNode objNode : participant){
            participants[i].setParticipantFK(objNode.asText());
            i++;
        }

        JsonNode registeredDate = node.get("registeredDate");
        RegisteredDates[] registeredDates = new RegisteredDates[registeredDate.size()];
        for(i = 0; i < registeredDate.size(); i++){
            registeredDates[i] = new RegisteredDates();
        }
        i = 0;
        for(JsonNode objNode : registeredDate){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = null;
            try{
                date = new Date(simpleDateFormat.parse(objNode.asText()).getTime());
            }
            catch(Exception e){
                log.error(e.toString());
                throw new IOException(e);
            }
            registeredDates[i].setRegisteredDate(date);
            i++;
        }
        RegisteredInformation registeredInformation = new RegisteredInformation();
        registeredInformation.setParticipants(participants);
        registeredInformation.setRegisteredChallenges(registeredChallenges);
        registeredInformation.setRegisteredDates(registeredDates);
        return registeredInformation;
    }

}
