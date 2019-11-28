package com.webapp.wooriga.mybatis.auth.dao;

import com.webapp.wooriga.mybatis.auth.mapper.CodeUserMapper;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CodeUserDAOImpl implements CodeUserDAO {
    private CodeUserMapper codeUserMapper;
    public CodeUserDAOImpl(){}
    @Autowired
    public CodeUserDAOImpl(CodeUserMapper codeUserMapper){this.codeUserMapper = codeUserMapper;}

    @Override
    public Long getUidFromCode(String code){
        return codeUserMapper.getUidFromCode(code);
    }
    @Override
    public int checkUser(long uid) {
        return codeUserMapper.checkUser(uid);
    }

    public long getUid(String code) { return codeUserMapper.getUid(code); }

    @Override
    public String getCode(long uid) {
        return codeUserMapper.getCode(uid);
    }

    @Override
    public void insertCodeUser(CodeUser codeuser) {
        codeUserMapper.insertCodeUser(codeuser);
    }

    @Override
    public void updateChief(CodeUser codeUser){codeUserMapper.updateChief(codeUser);}
}
