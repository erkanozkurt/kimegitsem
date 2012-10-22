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


public class AuthenticationInterceptor extends BaseInterceptor implements StrutsStatics {

	protected Log logger=LogFactory.getLog(AuthenticationInterceptor.class);


	@Override
	public String intercept(ActionInvocation action) throws Exception {
		String loginResponse="login";
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		logger.debug("authentication interceptor");
		UserContext context=(UserContext)action.getInvocationContext().getSession().get(ApplicationConstants.USER_CONTEXT_KEY);
		if(context==null || context.isLoggedIn()==false){
			logger.debug("return login page");
			return loginResponse;
		}
		return action.invoke();
	}

}