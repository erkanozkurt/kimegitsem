package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TblConversationReply generated by hbm2java
 */
public class TblConversationReply implements java.io.Serializable {

	private int replyId;
	private TblSubscriber tblSubscriber;
	private String replyText;
	private Date dateSent;
	private short status;
	private Integer conversationId;

	public TblConversationReply() {
	}

	public TblConversationReply(int replyId, TblSubscriber tblSubscriber,
			String replyText, Date dateSent, short status) {
		this.replyId = replyId;
		this.tblSubscriber = tblSubscriber;
		this.replyText = replyText;
		this.dateSent = dateSent;
		this.status = status;
	}

	public int getReplyId() {
		return this.replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public TblSubscriber getTblSubscriber() {
		return this.tblSubscriber;
	}

	public void setTblSubscriber(TblSubscriber tblSubscriber) {
		this.tblSubscriber = tblSubscriber;
	}

	public String getReplyText() {
		return this.replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
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

	public Integer getConversationId() {
		return conversationId;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

}
