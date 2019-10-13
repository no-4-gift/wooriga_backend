package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQ {
    private long faqId;
    private String title;
    private String content;
    public FAQ(long faqId, String title, String content){
        this.faqId = faqId;
        this.title = title;
        this.content = content;
    }
}
