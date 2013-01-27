package com.persona.kg.interceptor;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;


public class FakeUserInterceptor extends AbstractInterceptor{
	@Autowired
	private SubscriberDAO subscriberDAO;
	
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		if(ApplicationConstants.DEV_MODE){
			UserContext context=(UserContext)action.getInvocationContext().getSession().get(ApplicationConstants.USER_CONTEXT_KEY);
			if(context.isLoggedIn()==false){
				context.setLoggedIn(true);
				context.setAuthenticatedUser(subscriberDAO.retrieveUserById(1));
				context.setFacebookAccessToken("asdasdsdasdsad");
				context.putObject(ApplicationConstants.FRIENDLIST_KEY, subscriberDAO.retrieveFriendsBySubscriberId(context.getAuthenticatedUser().getSubscriberId()));
			}
		}
		return action.invoke();
	}

	public SubscriberDAO getSubscriberDAO() {
		return subscriberDAO;
	}

	public void setSubscriberDAO(SubscriberDAO subscriberDAO) {
		this.subscriberDAO = subscriberDAO;
	}
	
}