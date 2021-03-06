package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblFriendship generated by hbm2java
 */
public class TblFriendship implements java.io.Serializable {

	private TblFriendshipId id;
	private TblSubscriber tblSubscriberByInitiatorId;
	private TblSubscriber tblSubscriberByFriendId;

	public TblFriendship() {
	}

	public TblFriendship(TblFriendshipId id,
			TblSubscriber tblSubscriberByInitiatorId,
			TblSubscriber tblSubscriberByFriendId) {
		this.id = id;
		this.tblSubscriberByInitiatorId = tblSubscriberByInitiatorId;
		this.tblSubscriberByFriendId = tblSubscriberByFriendId;
	}

	public TblFriendshipId getId() {
		return this.id;
	}

	public void setId(TblFriendshipId id) {
		this.id = id;
	}

	public TblSubscriber getTblSubscriberByInitiatorId() {
		return this.tblSubscriberByInitiatorId;
	}

	public void setTblSubscriberByInitiatorId(
			TblSubscriber tblSubscriberByInitiatorId) {
		this.tblSubscriberByInitiatorId = tblSubscriberByInitiatorId;
	}

	public TblSubscriber getTblSubscriberByFriendId() {
		return this.tblSubscriberByFriendId;
	}

	public void setTblSubscriberByFriendId(TblSubscriber tblSubscriberByFriendId) {
		this.tblSubscriberByFriendId = tblSubscriberByFriendId;
	}

}
