package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblCompliment generated by hbm2java
 */
public class TblCompliment implements java.io.Serializable {

	private int complimentId;
	private TblSubscriber tblSubscriber;
	private TblComplimentType tblComplimentType;
	private String additionalComment;
	private int itemId;

	public TblCompliment() {
	}

	public TblCompliment(int complimentId, TblSubscriber tblSubscriber,
			TblComplimentType tblComplimentType, int itemId) {
		this.complimentId = complimentId;
		this.tblSubscriber = tblSubscriber;
		this.tblComplimentType = tblComplimentType;
		this.itemId = itemId;
	}

	public TblCompliment(int complimentId, TblSubscriber tblSubscriber,
			TblComplimentType tblComplimentType, String additionalComment,
			int itemId) {
		this.complimentId = complimentId;
		this.tblSubscriber = tblSubscriber;
		this.tblComplimentType = tblComplimentType;
		this.additionalComment = additionalComment;
		this.itemId = itemId;
	}

	public int getComplimentId() {
		return this.complimentId;
	}

	public void setComplimentId(int complimentId) {
		this.complimentId = complimentId;
	}

	public TblSubscriber getTblSubscriber() {
		return this.tblSubscriber;
	}

	public void setTblSubscriber(TblSubscriber tblSubscriber) {
		this.tblSubscriber = tblSubscriber;
	}

	public TblComplimentType getTblComplimentType() {
		return this.tblComplimentType;
	}

	public void setTblComplimentType(TblComplimentType tblComplimentType) {
		this.tblComplimentType = tblComplimentType;
	}

	public String getAdditionalComment() {
		return this.additionalComment;
	}

	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
