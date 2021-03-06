package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TblComment generated by hbm2java
 */
public class TblComment implements java.io.Serializable {

	private int commentId;
	private TblSubscriber tblSubscriber;
	private TblPoi tblPoi;
	private String comment;
	private short status;
	private Date dateAdded;
	private int tupCount;
	private int tdownCount;

	public TblComment() {
	}

	public TblComment(int commentId, TblSubscriber tblSubscriber,
			TblPoi tblPoi, String comment, short status, Date dateAdded) {
		this.commentId = commentId;
		this.tblSubscriber = tblSubscriber;
		this.tblPoi = tblPoi;
		this.comment = comment;
		this.status = status;
		this.dateAdded = dateAdded;
	}

	public int getCommentId() {
		return this.commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public TblSubscriber getTblSubscriber() {
		return this.tblSubscriber;
	}

	public void setTblSubscriber(TblSubscriber tblSubscriber) {
		this.tblSubscriber = tblSubscriber;
	}

	public TblPoi getTblPoi() {
		return this.tblPoi;
	}

	public void setTblPoi(TblPoi tblPoi) {
		this.tblPoi = tblPoi;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public int getTupCount() {
		return tupCount;
	}

	public void setTupCount(int tupCount) {
		this.tupCount = tupCount;
	}

	public int getTdownCount() {
		return tdownCount;
	}

	public void setTdownCount(int tdownCount) {
		this.tdownCount = tdownCount;
	}


}
