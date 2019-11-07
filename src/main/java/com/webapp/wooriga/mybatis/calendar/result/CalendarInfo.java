package com.webapp.wooriga.mybatis.calendar.result;

import java.sql.Date;

public class CalendarInfo {
    private String emptyDate;
    private String color;
    private String name;
    private String relationship;
    private String profile;

    public CalendarInfo(String emptyDate,String color, String name, String relationship, String profile){
        this.emptyDate = emptyDate;
        this.color = color;
        this.name = name;
        this.relationship = relationship;
        this.profile = profile;
    }
    public CalendarInfo(){

    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEmptyDate(String emptyDate) {
        this.emptyDate = emptyDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getEmptyDate() {
        return emptyDate;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getProfile() {
        return profile;
    }
}
