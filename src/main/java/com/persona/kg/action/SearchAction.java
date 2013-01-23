package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.CategoryDAO;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.common.CachedResources;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblCity;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.model.Subscriber;

public class SearchAction extends BaseAction implements SessionAware {
	private String what;
	private final static String DEF_WHAT = "Ne";
	private String where;
	private final static String DEF_WHERE = "Nerede";
	private String what_widget;
	private String where_widget;

	private List<TblPoi> searchResult;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private PoiDAO poiDao;
	@Autowired
	private CachedResources cachedResources;
	private Integer category;
	private Integer city;

	public String filter() {
		if (category != null) {
			TblCategory tblcategory = new TblCategory();
			tblcategory.setCategoryId(category);
			TblCategory cachedCategory = cachedResources.getCategoryMap().get(
					tblcategory.getCategoryId());
			if (cachedCategory != null) {
				String categoryList = cachedResources
						.getSubcategoryClause(cachedCategory);

				searchResult = poiDao.retrievePoisByCategoryWithSubcategories(
						categoryList, 12);
			}
		}
		return "success";
	}

	public String search() {
		if (logger.isDebugEnabled()) {
			logger.debug("search will be executed with parameters [what] "
					+ what + " [where] " + where);
		}
		String categoryClause = null;
		String placeId = null;
		if (!DEF_WHAT.equals(what_widget)) {
			categoryClause = getCachedResources()
					.getCategoryInStatementByCategoryName(what_widget);
		}
		if (!DEF_WHERE.equals(where_widget)) {
			placeId = getCachedResources().getPlaceIdByPlaceName(where_widget);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("search will be executed with parameters [categoryList] "
					+ categoryClause + " [placeList] " + placeId);
		}
		searchResult = poiDao.searchPoi(categoryClause, placeId, 0, 9);
		what = "";
		where = "";
		return "success";
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

	public List<TblPoi> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<TblPoi> searchResult) {
		this.searchResult = searchResult;
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

	public CachedResources getCachedResources() {
		return cachedResources;
	}

	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getWhat_widget() {
		return what_widget;
	}

	public void setWhat_widget(String what_widget) {
		this.what_widget = what_widget;
	}

	public String getWhere_widget() {
		return where_widget;
	}

	public void setWhere_widget(String where_widget) {
		this.where_widget = where_widget;
	}

}
