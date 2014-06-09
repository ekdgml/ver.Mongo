package com.namoo.social.web.controller.pres;

import com.namoo.social.domain.User;

public class PresUser {
	//
	private User user;
	private boolean followed;
	private boolean following;
	
	public PresUser(User user) {
		//
		this.user = user;
	}
	
	public String getUserId() {
		return user.getUserId();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public String getName() {
		return user.getName();
	}
	
	public boolean isFollowed() {
		return followed;
	}
	
	public boolean isFollowing() {
		return following;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

}
