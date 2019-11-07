package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.challenge.mapper.ParticipantsMapper;
import com.webapp.wooriga.mybatis.vo.Participants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantsDAOImpl implements ParticipantsDAO {
    private ParticipantsMapper participantsMapper;

    @Autowired
    public ParticipantsDAOImpl(ParticipantsMapper participantsMapper){
        this.participantsMapper = participantsMapper;
    }
    @Override
    public void insertParticipants(Participants participants){
        participantsMapper.insertParticipants(participants);
    }
}
