package com.persona.kg.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;


public class ContextInterceptor extends AbstractInterceptor{
	protected Log logger = LogFactory.getLog(ContextInterceptor.class);
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		UserContext userContext=null;
		if(!action.getInvocationContext().getSession().containsKey(ApplicationConstants.USER_CONTEXT_KEY)){
			userContext=new UserContext();
			userContext.setLoggedIn(false);
			action.getInvocationContext().getSession().put(ApplicationConstants.USER_CONTEXT_KEY, userContext);
		}else{
			userContext=(UserContext)action.getInvocationContext().getSession().get(ApplicationConstants.USER_CONTEXT_KEY);
		}
		String userId="unauthenticated";
		if(userContext.isLoggedIn()){
			userId=""+userContext.getAuthenticatedUser().getSubscriberId();
		}
		final HttpServletRequest request = (HttpServletRequest) ActionContext
        .getContext().get(ServletActionContext.HTTP_REQUEST);
		
		logger.debug("userid:"+userId+" ip:"+request.getRemoteHost()+" requested_resource:" +request.getRequestURL());
		
		return action.invoke();
	}
	
}