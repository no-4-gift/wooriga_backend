package com.webapp.wooriga.mybatis.auth.dao;

import com.webapp.wooriga.mybatis.vo.CodeUser;
import org.apache.ibatis.annotations.Param;

public interface CodeUserDAO {
    long getUid(String code);

    String getCode(long uid);

    void insertCodeUser(CodeUser codeuser);

    Long getUidFromCode(@Param("code")String code);

    int checkTrueUserNum(long uid);

    void updateChief(CodeUser codeUser);
}
