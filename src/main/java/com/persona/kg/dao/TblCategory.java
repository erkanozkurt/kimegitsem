package com.persona.kg.dao;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TblCategory generated by hbm2java
 */
public class TblCategory implements java.io.Serializable {

	private Integer categoryId;
	private int parentId;
	private String categoryName;
	private Set tblPoiCategories = new HashSet(0);
	private TblCategory parent;
	private int status;
	private List<TblCategory> childs=new ArrayList<TblCategory>();
	
	public TblCategory() {
	}

	public TblCategory(int parentId, String categoryName) {
		this.parentId = parentId;
		this.categoryName = categoryName;
	}

	public TblCategory(int parentId, String categoryName, Set tblPoiCategories) {
		this.parentId = parentId;
		this.categoryName = categoryName;
		this.tblPoiCategories = tblPoiCategories;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Set getTblPoiCategories() {
		return this.tblPoiCategories;
	}

	public void setTblPoiCategories(Set tblPoiCategories) {
		this.tblPoiCategories = tblPoiCategories;
	}

	public TblCategory getParent() {
		return parent;
	}

	public void setParent(TblCategory parent) {
		this.parent = parent;
	}

	public List<TblCategory> getChilds() {
		return childs;
	}

	public void setChilds(List<TblCategory> childs) {
		this.childs = childs;
	}
	
	public void addChild(TblCategory child){
		childs.add(child);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<TblCategory> getParents() {
		List<TblCategory> parents=new ArrayList<TblCategory>();
		parents.add(this);
		TblCategory tmp=getParent();	
		
		while(tmp!=null){
			parents.add(tmp);
			tmp=tmp.getParent();
		}
		Collections.reverse(parents);
		return parents;
	}

}
