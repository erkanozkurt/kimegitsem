package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;
import org.w3c.dom.Document;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;
import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.model.Subscriber;
import com.persona.kg.CategoryDAO;
import com.persona.kg.dao.TblCategory;
import com.sun.org.apache.xpath.internal.operations.Gte;

public class MainPageAction extends BaseAction implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";
	@Autowired
	private CategoryDAO categoryDao;
	private TblCategory category;

	public static FacebookXmlRestClient getUserClient(HttpSession session) {
        return (FacebookXmlRestClient) session
                        .getAttribute(FACEBOOK_USER_CLIENT);
}
	public String addCategory(){
		System.out.print("aaa");
		System.out.print(category.getCategoryName());
		System.out.print("sss");
		String result="success";
		categoryDao.addCategory(category);
		return result;
	}
	public String execute() {
		logger.debug("execute");
       		return "show";
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}
	public TblCategory getCategory() {
		return category;
	}
	public void setCategory(TblCategory category) {
		this.category = category;
	}
	public CategoryDAO getCategoryDao() {
		return categoryDao;
	}
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	
}
