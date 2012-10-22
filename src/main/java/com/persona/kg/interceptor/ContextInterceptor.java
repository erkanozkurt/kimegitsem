package com.persona.kg.interceptor;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;


public class ContextInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		if(!action.getInvocationContext().getSession().containsKey(ApplicationConstants.USER_CONTEXT_KEY)){
			UserContext userContext=new UserContext();
			userContext.setLoggedIn(false);
			action.getInvocationContext().getSession().put(ApplicationConstants.USER_CONTEXT_KEY, userContext);
		}
		return action.invoke();
	}
	
}