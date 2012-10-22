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
import com.persona.kg.SubscriberDAO;
import com.persona.kg.TalkDAO;
import com.persona.kg.action.BaseAction;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.CachedResources;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblConversation;


public class TalkInterceptor extends BaseInterceptor implements StrutsStatics {

	@Autowired
	private TalkDAO talkDAO;
	@Autowired
	private CachedResources cachedResources;
	@Autowired
	private SubscriberDAO subscriberDAO;
	
	protected Log logger=LogFactory.getLog(CategoryInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation action) throws Exception {
		
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		logger.debug("enter");
		UserContext context=(UserContext)action.getInvocationContext().getSession().get(ApplicationConstants.USER_CONTEXT_KEY);
		final ActionContext actionContext = action.getInvocationContext (); 
		HttpServletRequest request = (HttpServletRequest) actionContext.get(HTTP_REQUEST);
		String cityId=null;
		if(request.getParameter(ApplicationConstants.TALK_CITY_KEY)!=null){
			cityId=request.getParameter(ApplicationConstants.TALK_CITY_KEY);
			context.putObject(ApplicationConstants.TALK_CITY_KEY, cityId);
		}else if(context.getObject(ApplicationConstants.TALK_CITY_KEY)!=null){
			cityId=(String)context.getObject(ApplicationConstants.TALK_CITY_KEY);
		}
		
		String districtId=null;
		if(request.getParameter(ApplicationConstants.TALK_DISTRICT_KEY)!=null){
			districtId=request.getParameter(ApplicationConstants.TALK_DISTRICT_KEY);
			context.putObject(ApplicationConstants.TALK_DISTRICT_KEY, districtId);
		}else if(context.getObject(ApplicationConstants.TALK_DISTRICT_KEY)!=null){
			districtId=(String)context.getObject(ApplicationConstants.TALK_DISTRICT_KEY);
		}
		
		if(cityId!=null){
			request.setAttribute(ApplicationConstants.DISTRICT_LIST_KEY, talkDAO.retrieveDistrictListWithConvCount(Integer.parseInt(cityId)));
		}else{
			request.setAttribute(ApplicationConstants.CITY_LIST_KEY, talkDAO.retrieveCityListWithConvCount());
		}
		
		//retrieve conversation if exists
		String talkId = request.getParameter(
				ApplicationConstants.TALK_ID_KEY);
		TblConversation conv = null;
		if (talkId != null) {
			try {
				conv = talkDAO.retrieveConversationsByConvID(Integer
						.parseInt(talkId));
				if (conv != null) {
					request.setAttribute("conversation", conv);
					request.setAttribute(
							"startUser",
							subscriberDAO.retrieveUserById(conv
									.getTblSubscriber().getSubscriberId()));
					request.setAttribute(
							"convReply",
							talkDAO.retrieveConversationReplies(
									conv.getConversationId(), 0, 10));
				}
			} catch (Exception e) {
				logger.warn("Conversation could not be found [convId] "
						+ talkId);
			}
		}
		
		
		if (conv == null) {
			districtId =(String) context.getObject(
					ApplicationConstants.TALK_DISTRICT_KEY);
			cityId =(String) context.getObject(
					ApplicationConstants.TALK_CITY_KEY);
			if (districtId != null) {
				request.setAttribute(
						"convList",
						talkDAO.retrieveConversationsByDistrictId(
								Integer.parseInt((String) districtId), 0, 10));
			} else if (cityId != null) {
				request.setAttribute(
						"convList",
						talkDAO.retrieveConversationsByCityId(
								Integer.parseInt((String) cityId), 0, 10));
			} else {
				request.setAttribute("convList",
						talkDAO.retrieveConversations(0, 10));
			}
		}
		String actionInvoke=action.invoke();
	
		logger.debug("exit");
		return actionInvoke;
	}
	
	public CachedResources getCachedResources() {
		return cachedResources;
	}

	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
	}

	public TalkDAO getTalkDAO() {
		return talkDAO;
	}

	public void setTalkDAO(TalkDAO talkDAO) {
		this.talkDAO = talkDAO;
	}
	public TalkInterceptor(){
		
	}

	public SubscriberDAO getSubscriberDAO() {
		return subscriberDAO;
	}

	public void setSubscriberDAO(SubscriberDAO subscriberDAO) {
		this.subscriberDAO = subscriberDAO;
	}
	
}