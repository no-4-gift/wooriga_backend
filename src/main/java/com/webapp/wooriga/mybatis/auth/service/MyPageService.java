package com.webapp.wooriga.mybatis.auth.service;

import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;
import com.webapp.wooriga.mybatis.vo.CodeUser;

public interface MyPageService {
    MyRecordInfo sendMyRecordInfo(String familyId, long uid) throws RuntimeException;
    void delegateChief(String familyId, long uid, long chiefId) throws RuntimeException;
    void updateChief(CodeUser codeUser) throws RuntimeException;
    CodeUser makeCodeUser(long chiefId,String familyId);
}
