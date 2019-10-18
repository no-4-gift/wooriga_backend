package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
	private long uid;
	private String name;
	private String email;
	private String profile;
	private String color;
	private Date birth;
	
	public User(long uid, String name) {
		this.uid = uid;
		this.name = name;
	}
	
}
