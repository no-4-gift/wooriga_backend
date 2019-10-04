package com.webapp.woriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
	private String name;
	private String email;
	private String profile;
	private String color;
	private Date birth;
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
}
