package com.webapp.wooriga.mybatis.vo;

public class FAQ {

    private long faqId;
    private String title;
    private String content;

    public long getFaqId() {
        return faqId;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public void setFaqId(long faqId){
        this.faqId = faqId;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
}
