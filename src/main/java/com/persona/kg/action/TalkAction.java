package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.google.code.facebookapi.schema.EducationInfo.Concentrations;
import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.CategoryDAO;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.TalkDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.CachedResources;
import com.persona.kg.common.ImageResizer;
import com.persona.kg.common.ObjectIdGenerator;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblComment;
import com.persona.kg.dao.TblConversation;
import com.persona.kg.dao.TblConversationReply;
import com.persona.kg.dao.TblImage;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;
import com.persona.kg.model.Subscriber;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;

public class TalkAction extends BaseAction implements SessionAware {

	@Autowired
	private SubscriberDAO subscriberDAO;
	@Autowired
	private PoiDAO poiDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private TalkDAO talkDAO;
	@Autowired
	private CachedResources cachedResources;
	private String userComment;
	private File imageFile;
	private TblCategory category;
	private List<TblPoi> poiList;
	private String categoryId;
	private String what_widget;
	private String where_widget;
	private String description;
	private String what;
	private String where;
	private String suggestion;
	private String suggestion_widget;
	private String replyText;

	public String show() {
		String result = "list";
		String talkId = getServletRequest().getParameter(
				ApplicationConstants.TALK_ID_KEY);
		TblConversation conv = null;
		if(getServletRequest().getAttribute("conversation")!=null){
			result = "show";
		}
		
		return result;
	}

	public String ask() {
		String result = "success";
		if (logger.isDebugEnabled()) {
			logger.debug(" what:  " + what + " where: " + where
					+ " description: " + description);
		}
		try {
			UserContext userContext = getUserContext();
			TblSubscriber subscriber = subscriberDAO
					.retrieveFacebookSubscriber(userContext.getFacebookId());

			if (userContext.isLoggedIn()) {
				TblConversation conv = new TblConversation();
				if (what != null && where != null) {
					conv.setCategory(Integer.parseInt(what));
					if (where.indexOf(",") > -1) {
						conv.setDistrictId(Integer.parseInt(where.substring(0,
								where.indexOf(","))));
						conv.setCityId(Integer.parseInt(where.substring(where
								.indexOf(",") + 1)));
					} else {
						conv.setCityId(Integer.parseInt(where));
					}

					String subject = " (" + what_widget + " / " + where_widget
							+ ") kime gitsem?";
					conv.setBody(description);
					conv.setDateStarted(new Date());
					conv.setStatus((short) 1);
					if (description != null && description.length() > 30) {
						subject = description.substring(0, 29) + "... "
								+ subject;
					} else if (description != null) {
						subject = description + subject;
					}
					conv.setSubject(subject);
					conv.setTblSubscriber(subscriber);
					if (talkDAO.storeConversation(conv)) {
						getServletRequest().setAttribute("conversation", conv);
						boolean facebookPublish = askFacebookSuggestion(
								userContext.getFacebookAccessToken(),
								conv.getConversationId());
						if (facebookPublish == true) {
							result = "success";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception caught!", e);
		}
		return result;
	}

	private boolean askFacebookSuggestion(String accessToken, Integer convId) {
		boolean result = false;
		try {
			FacebookClient facebookClient = new DefaultFacebookClient(
					accessToken);
			FacebookType publishResponse = facebookClient.publish(
					"me/"+ApplicationConstants.getProperty("facebookAsk"),
					Post.class,
					Parameter.with("obje", getApplicationContext()
							+ "/talk/show?talkId=" + convId));
			if (publishResponse != null && publishResponse.getId() != null
					&& publishResponse.getId().trim().length() > 0) {
				result = true;
			}
		} catch (Exception e) {
			logger.warn("Facebook publish exception", e);
		}
		return result;
	}

	public String suggest() {
		String result="success";
		logger.debug("suggest invoked");
		try{
			if(suggestion!=null){
				logger.debug("suggestion "+suggestion);
				Integer poiId=Integer.parseInt(suggestion);
				UserContext userContext = getUserContext();
				logger.debug("log1 "+suggestion);
				FacebookClient facebookClient = new DefaultFacebookClient(userContext.getFacebookAccessToken());
				TblPoi poi=poiDAO.findPoiById(poiId);
				
				String publishUrl= ApplicationConstants.getContext()+"in/"+poi.getUniqueIdentifier();
				logger.debug("after find poi "+publishUrl);
				
				FacebookType publishResponse = facebookClient
						.publish(
								"/me/"+ApplicationConstants.getProperty("facebookSuggest"),
								Post.class,
								Parameter.with("obje", publishUrl));
				logger.debug("after publish "+ publishUrl);
				if(publishResponse!=null && publishResponse.getId()!=null && publishResponse.getId().trim().length()>0){
					result="success";
				}else{
					addActionError("error");
				}
			}
		}catch(Exception e){
			logger.warn("Facebook publish exception",e);
			addActionError("error");
		}
		logger.debug("suggest exit");
		return result;
	}

	public String addWatch() {
		String result = "success";
		TblWatchList watch = new TblWatchList();
		String talkId = getServletRequest().getParameter(
				ApplicationConstants.TALK_ID_KEY);
		UserContext context = getUserContext();
		if (talkId != null && context.isLoggedIn()) {
			watch.setDateAdded(new Date());
			watch.setItemId(Integer.parseInt(talkId));
			watch.setTblSubscriber(context.getAuthenticatedUser());
			watch.setItemType(ApplicationConstants.TALK_TYPE);
			subscriberDAO.addWatch(watch);
		}
		return result;
	}
	
	public String reply(){
		String result="show";
		TblConversationReply reply=new TblConversationReply();
		String talkId = getServletRequest().getParameter(
				ApplicationConstants.TALK_ID_KEY);
		try{
		reply.setConversationId(Integer.parseInt(talkId));
		reply.setDateSent(new Date());
		reply.setReplyText(replyText);
		reply.setTblSubscriber(getUserContext().getAuthenticatedUser());
		reply.setStatus((short)2);
		if(talkDAO.storeReply(reply)){
			getServletRequest().setAttribute(
					"convReply",
					talkDAO.retrieveConversationReplies(
							Integer.parseInt(talkId), 0, 10));
			replyText="";
		}else{
			addActionError("Cevap kaydedilemedi!");
		}
		}catch(Exception e){
			logger.warn("could not store conversation reply",e);
		}
		return result;
	}

	public SubscriberDAO getSubscriberDAO() {
		return subscriberDAO;
	}

	public void setSubscriberDAO(SubscriberDAO subscriberDAO) {
		this.subscriberDAO = subscriberDAO;
	}

	public PoiDAO getPoiDAO() {
		return poiDAO;
	}

	public void setPoiDAO(PoiDAO poiDAO) {
		this.poiDAO = poiDAO;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public TblCategory getCategory() {
		return category;
	}

	public void setCategory(TblCategory category) {
		this.category = category;
	}

	public List<TblPoi> getPoiList() {
		return poiList;
	}

	public void setPoiList(List<TblPoi> poiList) {
		this.poiList = poiList;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public TalkDAO getTalkDAO() {
		return talkDAO;
	}

	public void setTalkDAO(TalkDAO talkDAO) {
		this.talkDAO = talkDAO;
	}

	public CachedResources getCachedResources() {
		return cachedResources;
	}

	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
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

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getSuggestion_widget() {
		return suggestion_widget;
	}

	public void setSuggestion_widget(String suggestion_widget) {
		this.suggestion_widget = suggestion_widget;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

}
