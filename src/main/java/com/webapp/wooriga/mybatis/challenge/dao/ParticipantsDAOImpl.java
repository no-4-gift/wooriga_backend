package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.challenge.mapper.ParticipantsMapper;
import com.webapp.wooriga.mybatis.vo.Participants;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Override
    public List<Participants> selectParticipants(@Param("registeredId") long registeredId){
        return participantsMapper.selectParticipants(registeredId);
    }
    @Override
    public int selectUserIsCorrectParticipant(@Param("registeredId") long registeredId,@Param("uid")long uid){
        return participantsMapper.selectUserIsCorrectParticipant(registeredId,uid);
    }
}
