package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TblMessage generated by hbm2java
 */
public class TblMessage implements java.io.Serializable {

	private int messageId;
	private TblSubscriber tblSubscriberByRecipientId;
	private TblSubscriber tblSubscriberBySenderId;
	private String message;
	private String subject;
	private Date sendDate;
	private Short state;
	private Short senderType;
	private Short recipientType;
	private int mainMessageId;
	
	public TblMessage() {
	}

	public TblMessage(int messageId, TblSubscriber tblSubscriberByRecipientId,
			TblSubscriber tblSubscriberBySenderId, Date sendDate) {
		this.messageId = messageId;
		this.tblSubscriberByRecipientId = tblSubscriberByRecipientId;
		this.tblSubscriberBySenderId = tblSubscriberBySenderId;
		this.sendDate = sendDate;
	}

	public TblMessage(int messageId, TblSubscriber tblSubscriberByRecipientId,
			TblSubscriber tblSubscriberBySenderId, String message,
			String subject, Date sendDate, Short state, Short senderType,
			Short recipientType) {
		this.messageId = messageId;
		this.tblSubscriberByRecipientId = tblSubscriberByRecipientId;
		this.tblSubscriberBySenderId = tblSubscriberBySenderId;
		this.message = message;
		this.subject = subject;
		this.sendDate = sendDate;
		this.state = state;
		this.senderType = senderType;
		this.recipientType = recipientType;
	}

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public TblSubscriber getTblSubscriberByRecipientId() {
		return this.tblSubscriberByRecipientId;
	}

	public void setTblSubscriberByRecipientId(
			TblSubscriber tblSubscriberByRecipientId) {
		this.tblSubscriberByRecipientId = tblSubscriberByRecipientId;
	}

	public TblSubscriber getTblSubscriberBySenderId() {
		return this.tblSubscriberBySenderId;
	}

	public void setTblSubscriberBySenderId(TblSubscriber tblSubscriberBySenderId) {
		this.tblSubscriberBySenderId = tblSubscriberBySenderId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Short getSenderType() {
		return this.senderType;
	}

	public void setSenderType(Short senderType) {
		this.senderType = senderType;
	}

	public Short getRecipientType() {
		return this.recipientType;
	}

	public void setRecipientType(Short recipientType) {
		this.recipientType = recipientType;
	}

	public int getMainMessageId() {
		return mainMessageId;
	}

	public void setMainMessageId(int mainMessageId) {
		this.mainMessageId = mainMessageId;
	}

}
