package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TblComplimentType generated by hbm2java
 */
public class TblComplimentType implements java.io.Serializable {

	private int complimentTypeId;
	private short itemType;
	private String compText;
	private short status;
	private Set tblCompliments = new HashSet(0);

	public TblComplimentType() {
	}

	public TblComplimentType(int complimentTypeId, short itemType,
			String compText, short status) {
		this.complimentTypeId = complimentTypeId;
		this.itemType = itemType;
		this.compText = compText;
		this.status = status;
	}

	public TblComplimentType(int complimentTypeId, short itemType,
			String compText, short status, Set tblCompliments) {
		this.complimentTypeId = complimentTypeId;
		this.itemType = itemType;
		this.compText = compText;
		this.status = status;
		this.tblCompliments = tblCompliments;
	}

	public int getComplimentTypeId() {
		return this.complimentTypeId;
	}

	public void setComplimentTypeId(int complimentTypeId) {
		this.complimentTypeId = complimentTypeId;
	}

	public short getItemType() {
		return this.itemType;
	}

	public void setItemType(short itemType) {
		this.itemType = itemType;
	}

	public String getCompText() {
		return this.compText;
	}

	public void setCompText(String compText) {
		this.compText = compText;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Set getTblCompliments() {
		return this.tblCompliments;
	}

	public void setTblCompliments(Set tblCompliments) {
		this.tblCompliments = tblCompliments;
	}

}
