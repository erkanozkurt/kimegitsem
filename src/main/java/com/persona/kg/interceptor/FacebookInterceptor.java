package com.persona.kg.interceptor;

import java.util.Enumeration;
import java.util.Iterator;
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

public class FacebookInterceptor extends BaseInterceptor implements
		StrutsStatics {

	protected Log logger = LogFactory.getLog(CategoryInterceptor.class);

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		String result = "error";
		logger.debug("enter");
		try {
			final HttpServletRequest request = (HttpServletRequest) ActionContext
            .getContext().get(ServletActionContext.HTTP_REQUEST);
			String userAgent=request.getHeader("user-agent");
			logger.debug("user-agent: "+userAgent);
			if(userAgent!=null && userAgent.indexOf("facebook")>-1){
				request.setAttribute("facebookClient","true");
			}else{
				request.setAttribute("facebookClient","false");
			}
			result = action.invoke();
		} catch (Exception e) {
			logger.error("Struts received error", e);
			result = "error";
		}
		logger.debug("exit");
		return result;
	}

}