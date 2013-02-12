package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;
import org.w3c.dom.Document;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;
import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.CategoryDAO;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.CachedResources;
import com.persona.kg.common.HibernateUtil;
import com.persona.kg.common.JsonObject;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblPoiCategory;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.model.Subscriber;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.sun.org.apache.xpath.internal.operations.Gte;

public class AjaxAction extends BaseAction implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	private final static int MAX_RESULT=10;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private PoiDAO poiDao;
	private String what;
	private String where;
	private String suggestion;
	private String term;
	private List<TblPoi> poiList;
 	private JsonObject jsonResult;
 	private JsonObject jsonResult2;
 	private TblPoi poi;
	@Autowired
	private CachedResources cachedResources;
 	

	public CachedResources getCachedResources() {
		return cachedResources;
	}
	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
	}
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}

	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}


	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		if(term!=null){
			try {
				term=URLDecoder.decode(term, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		this.term = term;
	}


	public Map<String,String> getJson() {
		return json;
	}
	public void setJson(Map<String,String> json) {
		this.json = json;
	}


	private Map<String,String> json;
	private Map<String,String> json2;
	public String placeList(){
		logger.debug("term: "+term);
		json=new HashMap<String,String>();
		Map<String, String> placeList=cachedResources.getMergedPlaceList();
		boolean searchString=false;
		if(term!=null && term.length()>0){
			searchString=true;
			term=term.toLowerCase();
		}

		Iterator<String> placeIterator=placeList.keySet().iterator();
		while(placeIterator.hasNext() && json.size()<MAX_RESULT){
			String placeId=placeIterator.next();
			String placeName=placeList.get(placeId);
			if(searchString){
				if(placeName.toLowerCase().indexOf(term)>-1){
					json.put(placeId,placeName);
				}
			}else{
				json.put(placeId,placeName);
			}
		}		
		setNull();
		return SUCCESS;
	}
	public String categoryList(){
		logger.debug("received term: "+term);
		json=new HashMap<String,String>();
		Map<Integer, TblCategory> categories=cachedResources.getCategoryMap();
		boolean searchString=false;
		if(term!=null && term.length()>0){
			searchString=true;
			term=term.toLowerCase();
		}

		Iterator<TblCategory> categoryIterator=categories.values().iterator();
		while(categoryIterator.hasNext() && json.size()<MAX_RESULT){
			TblCategory category=categoryIterator.next();

			if(searchString){
				if(category.getCategoryName().toLowerCase().indexOf(term)>-1){
					json.put(""+category.getCategoryId(),category.getCategoryName());
				}
			}else{
				json.put(""+category.getCategoryId(),category.getCategoryName());
			}
		}
		setNull();
		return SUCCESS;
	}

	public String categoryListAll(){
		logger.debug("received term: "+term);
		json=new HashMap<String,String>();
		Map<Integer, TblCategory> categories=cachedResources.getCategoryMap();

		Iterator<TblCategory> categoryIterator=categories.values().iterator();
		while(categoryIterator.hasNext()){
			TblCategory category=categoryIterator.next();
			json.put(""+category.getCategoryId(),category.getCategoryName());
		}
		setNull();
		return SUCCESS;
	}
	public CategoryDAO getCategoryDao() {
		return categoryDao;
	}
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public PoiDAO getPoiDao() {
		return poiDao;
	}
	public void setPoiDao(PoiDAO poiDao) {
		this.poiDao = poiDao;
	}
	public String poiList(){
		jsonResult=new JsonObject();
		json=new HashMap<String,String>();
		List<TblPoi> list=poiDao.searchPoiByName(term,5);
		if(list!=null && list.size()>0){
			Iterator<TblPoi> poiIterator=list.iterator();
			while(poiIterator.hasNext()){
				TblPoi poi=poiIterator.next();
				json.put(""+poi.getPoiId(), poi.getPoiName());
			}
			jsonResult.setMap(json);
		}

		return SUCCESS;
	}

	public String poiDetails(){
		String result="success";
		if(poi!=null && poi.getPoiId()>0){
			poi=poiDao.findPoiById(poi.getPoiId());
			if(poi!=null){
				List<TblCategory> categoryList=poiDao.retrieveCategoriesByPoi(poi);
				if(categoryList!=null && categoryList.size()>0){
					poi.setCategory(categoryList.get(0).getCategoryId());
					poi.setCategories(null);
					poi.setAdministrator(null);
					poi.setComments(null);
					poi.setImages(null);
					poi.setTblAttributeValues(null);
					poi.setTblComments(null);
					poi.setTblDistrict(null);
					poi.setTblListItemses(null);
					poi.setTblPoiCategories(null);
					poi.setTblSubdistrict(null);
				}
			}
		}
		return result;
	}
	private void setNull(){
		cachedResources=null;
		poiDao=null;
		categoryDao=null;
	}
	public List<TblPoi> getPoiList() {
		return poiList;
	}
	public void setPoiList(List<TblPoi> poiList) {
		this.poiList = poiList;
	}
	public JsonObject getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(JsonObject jsonResult) {
		this.jsonResult = jsonResult;
	}
	public TblPoi getPoi() {
		return poi;
	}
	public void setPoi(TblPoi poi) {
		this.poi = poi;
	}




}