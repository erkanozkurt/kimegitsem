package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
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
import com.persona.kg.common.StatConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblComment;
import com.persona.kg.dao.TblConversation;
import com.persona.kg.dao.TblConversationReply;
import com.persona.kg.dao.TblImage;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblMessage;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblPoiCategory;
import com.persona.kg.dao.TblPoiCategoryId;
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
	private TblPoi poi;
	public TblPoi getPoi() {
		return poi;
	}

	public void setPoi(TblPoi poi) {
		this.poi = poi;
	}

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
	private String suggestionVisibility;
	private String emailList;
	private String selectedFriends;
	private String suggestedCategory;
	private String suggestionPlace;
	private String id;
	private String poiid;

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
				TblCategory selectedCategory=categoryDAO.findCategoryById(suggestedCategory);
				String placeName=getCachedResources().getMergedPlaceList().get(suggestionPlace);
				if (suggestedCategory!=null && suggestionPlace != null) {
					conv.setCategory(Integer.parseInt(suggestedCategory));
					String[] placeArray=suggestionPlace.split("\\,");
					if(placeArray.length==3){
						conv.setDistrictId(Integer.parseInt(placeArray[1]));
						conv.setCityId(Integer.parseInt(placeArray[2]));
					}
					if(placeArray.length==2){
						conv.setDistrictId(Integer.parseInt(placeArray[0]));
						conv.setCityId(Integer.parseInt(placeArray[1]));
					}
					if(placeArray.length==1){
						conv.setCityId(Integer.parseInt(placeArray[0]));
					}

					if (suggestionPlace.indexOf(",") > -1) {

					} else {
						conv.setCityId(Integer.parseInt(suggestionPlace));
					}


					conv.setBody(description);
					conv.setDateStarted(new Date());
					conv.setStatus((short) 1);
					conv.setSubject(getSubjectForRequest(selectedCategory.getCategoryName()));
					conv.setTblSubscriber(userContext.getAuthenticatedUser());

					if (talkDAO.storeConversation(conv)) {



						if("A".equals(getSuggestionVisibility())){
							if(askFacebookSuggestion(userContext.getFacebookAccessToken(),conv.getConversationId())==false){
								addActionMessage("Facebook paylaşımı sırasında hata oluştu!");
							}else{
								addActionMessage("Tavsiyen facebookta başarıyla paylaşıldı!");
							}
						}else{
							String selectedPlaceName=getCachedResources().getMergedPlaceList().get(suggestionPlace);
							if(StringUtils.isNotBlank(emailList)){
								if(askMailSuggestion(selectedCategory,selectedPlaceName)==false){
									addActionMessage("Mail paylaşımı sırasında hata oluştu!");
								}else{
									addActionMessage("Mail gönderimi başarılı!");
								}

							}
							if(StringUtils.isNotBlank(selectedFriends)){
								if(askPrivateSuggestion(selectedCategory,selectedPlaceName)==false){
									addActionMessage("Seçilen arkadaşlara mesaj gönderimi sırasında hata oluştu!");
								}else{
									addActionMessage("Mesaj seçilen arkadaşlara başarıyla gönderildi!");
								}
							}
						}
					
						getServletRequest().setAttribute("conversation", conv);
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
		TblPoi poiToPublish=null;
		try{
			if(StringUtils.isNotBlank(suggestion)){
				logger.debug("suggestion "+suggestion);
				Integer poiId=Integer.parseInt(suggestion);
				logger.debug("log1 "+suggestion);
				TblPoi poi=poiDAO.findPoiById(poiId);
				poiToPublish=poi;

			}else{
				if(StringUtils.isNotBlank(suggestion_widget)){
					TblPoi newPoi=new TblPoi();
					newPoi.setUniqueIdentifier(escapeSpaces(suggestion_widget));
					newPoi.setPoiName(suggestion_widget);
					newPoi.setCategory(Integer.parseInt(suggestedCategory));
					newPoi.setDateAdded(new Date());
					if(suggestionPlace!=null){
						String[] placeList=suggestionPlace.split("\\,");
						if(placeList.length==3){
							newPoi.setSubdistrictId(Integer.parseInt(placeList[0]));
							newPoi.setDistrictId(Integer.parseInt(placeList[1]));
							newPoi.setCityId(Integer.parseInt(placeList[2]));
						}
						if(placeList.length==2){
							newPoi.setDistrictId(Integer.parseInt(placeList[0]));
							newPoi.setCityId(Integer.parseInt(placeList[1]));
						}
						if(placeList.length==1){
							newPoi.setCityId(Integer.parseInt(placeList[0]));
						}
					}
					poiDAO.addPoi(newPoi);
					TblPoiCategory poiCategory=new TblPoiCategory();
					TblPoiCategoryId poiCategoryId=new TblPoiCategoryId();
					poiCategoryId.setPoiId(newPoi.getPoiId());
					poiCategoryId.setCategoryId(Integer.parseInt(suggestedCategory));
					poiCategory.setId(poiCategoryId);
					poiDAO.addPoiCategory(poiCategory);
					poiToPublish=newPoi;
				}
			}
			if("A".equals(getSuggestionVisibility())){
				if(shareFacebookSuggestion(poiToPublish)==false){
					addActionMessage("Facebook paylaşımı sırasında hata oluştu!");
				}else{
					addActionMessage("Tavsiyen facebookta başarıyla paylaşıldı!");
				}
			}else{
				if(StringUtils.isNotBlank(emailList)){
					if(shareMailSuggestion(poiToPublish)==false){
						addActionMessage("Mail paylaşımı sırasında hata oluştu!");
					}else{
						addActionMessage("Mail gönderimi başarılı!");
					}

				}
				if(StringUtils.isNotBlank(selectedFriends)){
					if(sharePrivateSuggestion(poiToPublish)==false){
						addActionMessage("Seçilen arkadaşlara mesaj gönderimi sırasında hata oluştu!");
					}else{
						addActionMessage("Mesaj seçilen arkadaşlara başarıyla gönderildi!");
					}
				}
			}
			addStat(getUserContext().getAuthenticatedUser().getSubscriberId(),poi.getPoiId() , StatConstants.AT_SUGGEST, StatConstants.IT_POI,"");

		}catch(Exception e){
			logger.warn("Facebook publish exception",e);
			addActionError("error");
		}
		logger.debug("suggest exit");
		return result;
	}


	private boolean shareFacebookSuggestion(TblPoi poi){
		boolean result=true;
		UserContext userContext = getUserContext();
		String publishUrl= ApplicationConstants.getContext()+"in/"+poi.getUniqueIdentifier();
		logger.debug("after find poi "+publishUrl);
		FacebookClient facebookClient = new DefaultFacebookClient(userContext.getFacebookAccessToken());
		FacebookType publishResponse = facebookClient
				.publish(
						"/me/"+ApplicationConstants.getProperty("facebookSuggest"),
						Post.class,
						Parameter.with("obje", publishUrl));
		logger.debug("after publish "+ publishUrl);
		if(publishResponse!=null && publishResponse.getId()!=null && publishResponse.getId().trim().length()>0){
			result=true;
		}else{
			result=false;
		}
		logger.info("appLog tavsEt(genel) kullanici:" + userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " yayınla hizmAd:" + poi.getPoiName());
		addStat(getUserContext().getAuthenticatedUser().getSubscriberId(),poi.getPoiId(), StatConstants.AT_SUGGEST,StatConstants.IT_POI, "facebook genel tavsiye");
		return result;
	}

	private boolean sharePrivateSuggestion(TblPoi poi){
		boolean result=true;
		String[] list=null;
		String yazliste="";
		UserContext userContext = getUserContext();
		if(getSelectedFriends()!=null){
			list=getSelectedFriends().split("\\,");
			if(list!=null && list.length>0){
				for(String friend:list){
					TblMessage message=new TblMessage();
					TblSubscriber recipient=new TblSubscriber();
					recipient.setSubscriberId(Integer.parseInt(friend.trim()));
					message.setTblSubscriberByRecipientId(recipient);
					message.setTblSubscriberBySenderId(userContext.getAuthenticatedUser());
					List<TblCategory> categories=poiDAO.retrieveCategoriesByPoi(poi);
					message.setSubject(getSubjectForSuggestion(poi.getPoiName(), categories.get(0).getCategoryName()));					
					Map model = new HashMap();
					model.put("name", userContext.getAuthenticatedUser().getName());
					model.put("surname", userContext.getAuthenticatedUser().getSurname());
					model.put("profile","https://graph.facebook.com/"+userContext.getAuthenticatedUser().getFacebookId()+"/picture");
					model.put("logo",ApplicationConstants.getContext()+"img/suggestion/mail_logo.png");
					model.put("footer",ApplicationConstants.getContext()+"img/suggestion/mail_footer.png");
					model.put("description", description);
					model.put("poi", poi.getPoiName());
					model.put("url", ApplicationConstants.getContext()+"in/"+poi.getUniqueIdentifier());
					message.setMessage(getMergedTemplate("suggestion",model));
					message.setSendDate(new Date());
					subscriberDAO.sendMessage(message);
				}
			}
		}
		if(list!=null && list.length>0){
			for(int i=0;i<list.length;i++){
				yazliste+=list[i];
			}
		}
		addStat(getUserContext().getAuthenticatedUser().getSubscriberId(),poi.getPoiId(), StatConstants.AT_SUGGEST,StatConstants.IT_POI, "facebook özel tavsiye");
		logger.info("appLog tavsEt(özel - " + yazliste + ") kullanici:" + userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " hizmAd:" + poi.getPoiName());
		return result;
	}

	private String getSubjectForSuggestion(String poiName, String category){
		StringBuilder sb=new StringBuilder();
		UserContext context=getUserContext();
		sb.append(context.getAuthenticatedUser().getName()+" "+context.getAuthenticatedUser().getSurname());
		sb.append(" kimegitsem?com'da ");
		sb.append(poiName);
		sb.append("("+category+") tavsiye ediyor.");
		return sb.toString();
	}
	private String getSubjectForRequest( String category){
		StringBuilder sb=new StringBuilder();
		UserContext context=getUserContext();
		sb.append(context.getAuthenticatedUser().getName()+" "+context.getAuthenticatedUser().getSurname());
		sb.append(" kimegitsem?com'da ");
		sb.append(category+" tavsiyesi istiyor.");
		return sb.toString();
	}

	private boolean shareMailSuggestion(TblPoi poi){
		boolean result=true;
		String[] list = null;
		String yazliste="";
		UserContext userContext = getUserContext();
		if(emailList!=null){
			list=emailList.split("\\,");
			if(list!=null && list.length>0){
				for(String email:list){
					if(EmailValidator.getInstance().isValid(email)==true){
						Map model = new HashMap();
						model.put("name", userContext.getAuthenticatedUser().getName());
						model.put("surname", userContext.getAuthenticatedUser().getSurname());
						model.put("profile","https://graph.facebook.com/"+userContext.getAuthenticatedUser().getFacebookId()+"/picture");
						model.put("logo",ApplicationConstants.getContext()+"img/suggestion/mail_logo.png");
						model.put("footer",ApplicationConstants.getContext()+"img/suggestion/mail_footer.png");
						model.put("description", description);
						model.put("poi", poi.getPoiName());
						model.put("url", ApplicationConstants.getContext()+"in/"+poi.getUniqueIdentifier());
						List<TblCategory> categories=poiDAO.retrieveCategoriesByPoi(poi);
						sendMail(model, "suggestion", email, getSubjectForSuggestion(poi.getPoiName(), categories.get(0).getCategoryName()));
					}
				}
			}
		}
		if(list!=null && list.length>0){
			for(int i=0;i<list.length;i++){
				yazliste+=list[i];
			}
		}
		addStat(getUserContext().getAuthenticatedUser().getSubscriberId(),
				poi.getPoiId(), 
				StatConstants.AT_SUGGEST,
				StatConstants.IT_POI, 
				"mail yoluyla tavsiye");
		logger.info("appLog tavsEt(mail - " +  yazliste  + ")kullanici:" + userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " hizmAd:" + poi.getPoiName());
		return result;
	}	


	private boolean askMailSuggestion(TblCategory selectedCategory, String placeName){
		boolean result=true;
		String[] list=null;
		String yazliste="";
		UserContext userContext = getUserContext();
		if(emailList!=null){
			list=emailList.split("\\,");
			if(list!=null && list.length>0){
				for(String email:list){
					if(EmailValidator.getInstance().isValid(email)==true){
						Map model = new HashMap();
						model.put("name", userContext.getAuthenticatedUser().getName());
						model.put("surname", userContext.getAuthenticatedUser().getSurname());
						model.put("profile","https://graph.facebook.com/"+userContext.getAuthenticatedUser().getFacebookId()+"/picture");
						model.put("logo",ApplicationConstants.getContext()+"img/suggestion/mail_logo.png");
						model.put("category", selectedCategory.getCategoryName());
						model.put("place",placeName);
						model.put("footer",ApplicationConstants.getContext()+"img/suggestion/mail_footer.png");
						model.put("description", description);
						sendMail(model, "requestSuggestion", email, getSubjectForRequest(selectedCategory.getCategoryName()));
					}
				}
			}
		}
		if(list!=null && list.length>0){
			for(int i=0;i<list.length;i++){
				yazliste+=list[i];
			}
		}
		addStat(getUserContext().getAuthenticatedUser().getSubscriberId(),poi.getPoiId(), StatConstants.AT_REQUEST_SUGGESTION,StatConstants.IT_POI, "mail yoluyla tavsiye isteme");
		logger.info("appLog tavsİste(mail - " + yazliste + "kullanici:" + userContext.getAuthenticatedUser().getName() + userContext.getAuthenticatedUser().getSurname() + " hizmAd:" + poi.getPoiName());
		return result;
	}	

private boolean askPrivateSuggestion(TblCategory selectedCategory,String placeName){
		boolean result=true;
		String[] list=null;
		String yazliste="";
		UserContext userContext = getUserContext();
		if(getSelectedFriends()!=null){
			list=getSelectedFriends().split("\\,");
			if(list!=null && list.length>0){
				for(String friend:list){
					TblMessage message=new TblMessage();
					TblSubscriber recipient=new TblSubscriber();
					recipient.setSubscriberId(Integer.parseInt(friend.trim()));
					message.setTblSubscriberByRecipientId(recipient);
					message.setTblSubscriberBySenderId(userContext.getAuthenticatedUser());
					message.setSubject(getSubjectForRequest(selectedCategory.getCategoryName()));					
					Map model = new HashMap();
					model.put("name", userContext.getAuthenticatedUser().getName());
					model.put("surname", userContext.getAuthenticatedUser().getSurname());
					model.put("profile","https://graph.facebook.com/"+userContext.getAuthenticatedUser().getFacebookId()+"/picture");
					model.put("logo",ApplicationConstants.getContext()+"img/suggestion/mail_logo.png");
					model.put("footer",ApplicationConstants.getContext()+"img/suggestion/mail_footer.png");
					model.put("category", selectedCategory.getCategoryName());
					model.put("place",placeName);
					model.put("description", description);
					message.setMessage(getMergedTemplate("requestSuggestion",model));
					message.setSendDate(new Date());
					subscriberDAO.sendMessage(message);
				}
			}
		}
		if(list!=null && list.length>0){
			for(int i=0;i<list.length;i++){
				yazliste+=list[i];
			}
		}
		addStat(getUserContext().getAuthenticatedUser().getSubscriberId(),poi.getPoiId(), StatConstants.AT_REQUEST_SUGGESTION,StatConstants.IT_POI, "facebook özel tavsiye isteme");
		logger.info("appLog tavsIste(özel - " + yazliste + ") kullanici:" + userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " hizmAd:" + poi.getPoiName());
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

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getSuggestionVisibility() {
		return suggestionVisibility;
	}

	public void setSuggestionVisibility(String suggestionVisibility) {
		this.suggestionVisibility = suggestionVisibility;
	}

	public String getEmailList() {
		return emailList;
	}

	public void setEmailList(String emailList) {
		this.emailList = emailList;
	}

	public String getSelectedFriends() {
		return selectedFriends;
	}

	public void setSelectedFriends(String selectedFriends) {
		this.selectedFriends = selectedFriends;
	}

	public String getSuggestedCategory() {
		return suggestedCategory;
	}

	public void setSuggestedCategory(String suggestedCategory) {
		this.suggestedCategory = suggestedCategory;
	}

	public String getSuggestionPlace() {
		return suggestionPlace;
	}

	public void setSuggestionPlace(String suggestionPlace) {
		this.suggestionPlace = suggestionPlace;
	}

}