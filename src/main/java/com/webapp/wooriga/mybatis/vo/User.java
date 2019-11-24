package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private long uid;
	private String name;
	private String email;
	private String profile;
	private String color;
	private String birth;
	private String familyId;
	private String relationship;

	public User(long uid, String name, String email, String profile, String color, String birth) {
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.profile = profile;
		this.color = color;
		this.birth = birth;
	}

	public User(long uid, String name, String email, String profile, String color, String birth, String familyId, String relationship) {
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.profile = profile;
		this.color = color;
		this.birth = birth;
		this.familyId = familyId;
		this.relationship = relationship;
	}

}