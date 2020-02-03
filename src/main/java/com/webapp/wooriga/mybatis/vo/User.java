package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.auth.deserializer.UserDeserializer;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = UserDeserializer.class)
public class User {
	private long uid;
	private String username;
	private String email;
	private String profile;
	private String color;
	private String birth;
	private String familyId;
	private String relationship;
	private String authority;

	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(authority));
		return auth;
	}
	public void setAuthority(){
			this.authority = "ROLE_USER";
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String getPassword(){return null;}
*/
}