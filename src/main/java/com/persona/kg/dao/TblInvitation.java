package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TblInvitation generated by hbm2java
 */
public class TblInvitation implements java.io.Serializable {

	private int invitationId;
	private Integer subscriberId;
	private Integer poiId;
	private Integer invitationType;
	private String referenceCode;
	private Date dateSent;
	private short status;
	private String invited;

	public TblInvitation() {
	}

	public TblInvitation(int invitationId, String referenceCode, Date dateSent,
			short status, String invited) {
		this.invitationId = invitationId;
		this.referenceCode = referenceCode;
		this.dateSent = dateSent;
		this.status = status;
		this.invited = invited;
	}

	public TblInvitation(int invitationId, Integer subscriberId, Integer poiId,
			Integer invitationType, String referenceCode, Date dateSent,
			short status, String invited) {
		this.invitationId = invitationId;
		this.subscriberId = subscriberId;
		this.poiId = poiId;
		this.invitationType = invitationType;
		this.referenceCode = referenceCode;
		this.dateSent = dateSent;
		this.status = status;
		this.invited = invited;
	}

	public int getInvitationId() {
		return this.invitationId;
	}

	public void setInvitationId(int invitationId) {
		this.invitationId = invitationId;
	}

	public Integer getSubscriberId() {
		return this.subscriberId;
	}

	public void setSubscriberId(Integer subscriberId) {
		this.subscriberId = subscriberId;
	}

	public Integer getPoiId() {
		return this.poiId;
	}

	public void setPoiId(Integer poiId) {
		this.poiId = poiId;
	}

	public Integer getInvitationType() {
		return this.invitationType;
	}

	public void setInvitationType(Integer invitationType) {
		this.invitationType = invitationType;
	}

	public String getReferenceCode() {
		return this.referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public Date getDateSent() {
		return this.dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getInvited() {
		return this.invited;
	}

	public void setInvited(String invited) {
		this.invited = invited;
	}

}
