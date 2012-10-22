package com.persona.kg.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.persona.kg.CategoryDAO;
import com.persona.kg.action.BaseAction;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;


public abstract class BaseInterceptor extends AbstractInterceptor implements StrutsStatics {
	@Autowired
	private CacheManager cacheManager;

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public Cache getCache(){
		return cacheManager.getCache("default");
	}
}