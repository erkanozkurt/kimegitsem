package com.persona.kg.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.persona.kg.common.CachedResources;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;


public class CategoryInterceptor extends BaseInterceptor implements StrutsStatics {

	private CategoryDAO categoryDAO;
	@Autowired
	private CachedResources cachedResources;
	protected Log logger=LogFactory.getLog(CategoryInterceptor.class);
	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		UserContext context=(UserContext)action.getInvocationContext().getSession().get(ApplicationConstants.USER_CONTEXT_KEY);
		final ActionContext actionContext = action.getInvocationContext (); 
		HttpServletRequest request = (HttpServletRequest) actionContext.get(HTTP_REQUEST);
		context.setSelectedCategory(getSelecteCategoryFromCache(request.getParameter(ApplicationConstants.CATEGORY_ID_KEY)));
		
		logger.debug("exit");
	
		return action.invoke();
	}
	
	private TblCategory getSelecteCategoryFromCache(String selectedCategory){
		TblCategory category=null;
		Map<Integer,TblCategory> categories=cachedResources.getCategoryMap();
		
		Integer selectedCategoryKey=0;
		if(selectedCategory!=null){
			try{
				selectedCategoryKey=Integer.parseInt(selectedCategory);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		category=categories.get(selectedCategoryKey);
		return category;
	}

	public CachedResources getCachedResources() {
		return cachedResources;
	}

	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
	}
	
}