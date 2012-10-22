package com.persona.kg.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblSubscriber;

public class UserContext {
	private TblSubscriber authenticatedUser;
	private boolean loggedIn;
	private TblCategory selectedCategory;
	private String facebookAccessToken;
	private String facebookId;	
	private TblPoi selectedPoi;
	private Map<String, Object> sessionObjects=new HashMap<String, Object>();
	
	public TblPoi getSelectedPoi() {
		return selectedPoi;
	}
	public void setSelectedPoi(TblPoi selectedPoi) {
		this.selectedPoi = selectedPoi;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getFacebookAccessToken() {
		return facebookAccessToken;
	}
	public void setFacebookAccessToken(String facebookAccessToken) {
		this.facebookAccessToken = facebookAccessToken;
	}
	

	public TblSubscriber getAuthenticatedUser() {
		return authenticatedUser;
	}
	public void setAuthenticatedUser(TblSubscriber authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public TblCategory getSelectedCategory() {
		return selectedCategory;
	}
	public void setSelectedCategory(TblCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	public void putObject(String key, Object value){
		sessionObjects.put(key, value);
	}
	
	public Object getObject(String key){
		return sessionObjects.get(key);
	}
	
}
