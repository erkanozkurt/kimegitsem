package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TblTransactionLog generated by hbm2java
 */
public class TblTransactionLog implements java.io.Serializable {

	private int logId;
	private Integer subscriberId;
	private Integer poiId;
	private String logText;
	private Date logDate;

	public TblTransactionLog() {
	}

	public TblTransactionLog(int logId, Date logDate) {
		this.logId = logId;
		this.logDate = logDate;
	}

	public TblTransactionLog(int logId, Integer subscriberId, Integer poiId,
			String logText, Date logDate) {
		this.logId = logId;
		this.subscriberId = subscriberId;
		this.poiId = poiId;
		this.logText = logText;
		this.logDate = logDate;
	}

	public int getLogId() {
		return this.logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
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

	public String getLogText() {
		return this.logText;
	}

	public void setLogText(String logText) {
		this.logText = logText;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

}
