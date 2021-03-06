package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TblSubscriber generated by hbm2java
 */
public class TblSubscriber implements java.io.Serializable {

	private Integer subscriberId;
	private String facebookId;
	private String twitterId;
	private String googlePlusId;
	private String name;
	private String surname;
	private Date birthdate;
	private Short subscriberType;
	private Date joinDate;
	private Integer primaryLocation;
	private Integer secondaryLocation;
	private String gender;
	private Integer activated;
	private Set tblCompliments = new HashSet(0);
	private Set tblPoiAdministrators = new HashSet(0);
	private Set tblMessagesForRecipientId = new HashSet(0);
	private Set tblStatses = new HashSet(0);
	private Set tblConversationReplies = new HashSet(0);
	private Set tblLists = new HashSet(0);
	private Set tblFriendshipsForFriendId = new HashSet(0);
	private Set tblImages = new HashSet(0);
	private Set tblMessagesForSenderId = new HashSet(0);
	private Set tblWatchLists = new HashSet(0);
	private Set tblComments = new HashSet(0);
	private Set tblConversations = new HashSet(0);
	private Set tblFriendshipsForInitiatorId = new HashSet(0);
	private String email;
	public TblSubscriber() {
	}

	public TblSubscriber(Date joinDate) {
		this.joinDate = joinDate;
	}

	public TblSubscriber(String facebookId, String twitterId,
			String googlePlusId, String name, String surname, Date birthdate,
			Short subscriberType, Date joinDate, Integer primaryLocation,
			Integer secondaryLocation, String gender, Integer activated,
			Set tblCompliments, Set tblPoiAdministrators,
			Set tblMessagesForRecipientId, Set tblStatses,
			Set tblConversationReplies, Set tblLists,
			Set tblFriendshipsForFriendId, Set tblImages,
			Set tblMessagesForSenderId, Set tblWatchLists, Set tblComments,
			Set tblConversations, Set tblFriendshipsForInitiatorId) {
		this.facebookId = facebookId;
		this.twitterId = twitterId;
		this.googlePlusId = googlePlusId;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.subscriberType = subscriberType;
		this.joinDate = joinDate;
		this.primaryLocation = primaryLocation;
		this.secondaryLocation = secondaryLocation;
		this.gender = gender;
		this.activated = activated;
		this.tblCompliments = tblCompliments;
		this.tblPoiAdministrators = tblPoiAdministrators;
		this.tblMessagesForRecipientId = tblMessagesForRecipientId;
		this.tblStatses = tblStatses;
		this.tblConversationReplies = tblConversationReplies;
		this.tblLists = tblLists;
		this.tblFriendshipsForFriendId = tblFriendshipsForFriendId;
		this.tblImages = tblImages;
		this.tblMessagesForSenderId = tblMessagesForSenderId;
		this.tblWatchLists = tblWatchLists;
		this.tblComments = tblComments;
		this.tblConversations = tblConversations;
		this.tblFriendshipsForInitiatorId = tblFriendshipsForInitiatorId;
	}

	public Integer getSubscriberId() {
		return this.subscriberId;
	}

	public void setSubscriberId(Integer subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getFacebookId() {
		return this.facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getTwitterId() {
		return this.twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getGooglePlusId() {
		return this.googlePlusId;
	}

	public void setGooglePlusId(String googlePlusId) {
		this.googlePlusId = googlePlusId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Short getSubscriberType() {
		return this.subscriberType;
	}

	public void setSubscriberType(Short subscriberType) {
		this.subscriberType = subscriberType;
	}

	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Integer getPrimaryLocation() {
		return this.primaryLocation;
	}

	public void setPrimaryLocation(Integer primaryLocation) {
		this.primaryLocation = primaryLocation;
	}

	public Integer getSecondaryLocation() {
		return this.secondaryLocation;
	}

	public void setSecondaryLocation(Integer secondaryLocation) {
		this.secondaryLocation = secondaryLocation;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getActivated() {
		return this.activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

	public Set getTblCompliments() {
		return this.tblCompliments;
	}

	public void setTblCompliments(Set tblCompliments) {
		this.tblCompliments = tblCompliments;
	}

	public Set getTblPoiAdministrators() {
		return this.tblPoiAdministrators;
	}

	public void setTblPoiAdministrators(Set tblPoiAdministrators) {
		this.tblPoiAdministrators = tblPoiAdministrators;
	}

	public Set getTblMessagesForRecipientId() {
		return this.tblMessagesForRecipientId;
	}

	public void setTblMessagesForRecipientId(Set tblMessagesForRecipientId) {
		this.tblMessagesForRecipientId = tblMessagesForRecipientId;
	}

	public Set getTblStatses() {
		return this.tblStatses;
	}

	public void setTblStatses(Set tblStatses) {
		this.tblStatses = tblStatses;
	}

	public Set getTblConversationReplies() {
		return this.tblConversationReplies;
	}

	public void setTblConversationReplies(Set tblConversationReplies) {
		this.tblConversationReplies = tblConversationReplies;
	}

	public Set getTblLists() {
		return this.tblLists;
	}

	public void setTblLists(Set tblLists) {
		this.tblLists = tblLists;
	}

	public Set getTblFriendshipsForFriendId() {
		return this.tblFriendshipsForFriendId;
	}

	public void setTblFriendshipsForFriendId(Set tblFriendshipsForFriendId) {
		this.tblFriendshipsForFriendId = tblFriendshipsForFriendId;
	}

	public Set getTblImages() {
		return this.tblImages;
	}

	public void setTblImages(Set tblImages) {
		this.tblImages = tblImages;
	}

	public Set getTblMessagesForSenderId() {
		return this.tblMessagesForSenderId;
	}

	public void setTblMessagesForSenderId(Set tblMessagesForSenderId) {
		this.tblMessagesForSenderId = tblMessagesForSenderId;
	}

	public Set getTblWatchLists() {
		return this.tblWatchLists;
	}

	public void setTblWatchLists(Set tblWatchLists) {
		this.tblWatchLists = tblWatchLists;
	}

	public Set getTblComments() {
		return this.tblComments;
	}

	public void setTblComments(Set tblComments) {
		this.tblComments = tblComments;
	}

	public Set getTblConversations() {
		return this.tblConversations;
	}

	public void setTblConversations(Set tblConversations) {
		this.tblConversations = tblConversations;
	}

	public Set getTblFriendshipsForInitiatorId() {
		return this.tblFriendshipsForInitiatorId;
	}

	public void setTblFriendshipsForInitiatorId(Set tblFriendshipsForInitiatorId) {
		this.tblFriendshipsForInitiatorId = tblFriendshipsForInitiatorId;
	}

	public String getDisplayName(){
		return getName()+" "+ getSurname().substring(0,1)+".";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
