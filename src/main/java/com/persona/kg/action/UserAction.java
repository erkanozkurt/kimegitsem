package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.FileCopyUtils;

import com.persona.kg.common.CachedResources;
import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.StatConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblMessage;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblRate;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;
import com.persona.kg.model.Subscriber;
//import com.persona.kgadmin.dao.TblPoi;

public class UserAction extends BaseAction implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	
	public final static int RATE_LIKE = 1;
	public final static int RATE_DISLIKE = 2;
	private SubscriberDAO subscriberDAO;
	private TblPoi poi;
	private String mailAddressContainer;
	private static final Logger logger = Logger.getLogger(TblSubscriber.class);
	private List<TblMessage> messageList;
	private List<TblSubscriber> friendList;
	private int messageId;
	private List jsonList;
	private String id;
	private String mail;
	private String source;
	private int st=0;
	@Autowired
	private CachedResources cachedResources;


	public CachedResources getCachedResources() {
		return cachedResources;
	}

	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
	}

	public String profile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		TblSubscriber subscriber = null;
		String facebookId = request
				.getParameter(ApplicationConstants.USER_ID_KEY);
		String profileId = request
				.getParameter(ApplicationConstants.PROFILE_ID_KEY);
		if (facebookId == null) {
			facebookId = getUserContext().getFacebookId();
		}
		if (profileId != null) {
			subscriber = subscriberDAO.retrieveUserById(Integer
					.parseInt(profileId));
		}
		if (subscriber == null && facebookId != null) {
			subscriber = subscriberDAO.retrieveFacebookSubscriber(facebookId);
		}

		request.setAttribute("subscriber", subscriber);
		return "success";
	}

	public String follow() {
		return "success";
	}

	public String friends() {
		return "success";
	}

	public String addWatch() {
		String result = "success";
		TblWatchList watch = new TblWatchList();
		String profileId = getServletRequest().getParameter(
				ApplicationConstants.PROFILE_ID_KEY);
		UserContext context = getUserContext();
		if (profileId != null && context.isLoggedIn()) {
			watch.setDateAdded(new Date());
			watch.setItemId(Integer.parseInt(profileId));
			watch.setTblSubscriber(context.getAuthenticatedUser());
			watch.setItemType(ApplicationConstants.SUBSCRIBER_TYPE);
			subscriberDAO.addWatch(watch);
		}
		return result;
	}

	public UserContext getUserContext() {
		HttpServletRequest request = ServletActionContext.getRequest();
		UserContext context = (UserContext) request.getSession().getAttribute(
				ApplicationConstants.USER_CONTEXT_KEY);
		return context;
	}

	public String likeComment() {
		return voteComment(RATE_LIKE);
	}

	public String dislikeComment() {
		return voteComment(RATE_DISLIKE);
	}

	private String voteComment(int voteType) {
		String result = "success";
		String errorMessage = "";
		TblRate rate = new TblRate();
		String commentId = getServletRequest().getParameter(
				ApplicationConstants.COMMENT_ID_KEY);
		UserContext context = getUserContext();
		if (commentId != null && context.isLoggedIn()) {
			int subscriberId = context.getAuthenticatedUser().getSubscriberId();
			int intCommentId = Integer.parseInt(commentId);
			if (subscriberDAO.checkRate(subscriberId, intCommentId) == false) {
				rate.setItemId(intCommentId);
				rate.setOwnerId(subscriberId);
				rate.setRateType(voteType);
				subscriberDAO.storeRate(rate);
			} else {
				errorMessage = "Daha önce puanlanmış!";
			}
		} else {
			errorMessage = "Değerlendirme yapmak için giriş yapmanız gerekmektedir!";
		}
		if (errorMessage.length() > 0) {
			getServletRequest().setAttribute("errorMessage", errorMessage);
		}
		return result;
	}

	public String inviteFriends() {
		return "success";
	}

	public String sendInvitation() {
		final TblSubscriber authenticatedUser=getUserContext().getAuthenticatedUser();
		UserContext userContext = getUserContext();
		String[] addresses=null;
		String yazliste="";
		if (mailAddressContainer != null) {
			addresses=mailAddressContainer.split(",");
			for(int i=0;i<addresses.length;i++){
				final String address=addresses[i];
				MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage);
						// mail sending parameters
						message.setTo(address);
						message.setFrom("ylcnarsln@gmail.com");
						message.setSubject(authenticatedUser.getName()+" "+authenticatedUser.getSurname()+" sana kimegitsem?com’dan arkadaslik istegi gonderdi");
						Map model = new HashMap();
						model.put("name", authenticatedUser.getName());
						model.put("surname", authenticatedUser.getSurname());
						model.put("profile","https://graph.facebook.com/"+authenticatedUser.getFacebookId()+"/picture");
						model.put("logo",ApplicationConstants.getContext()+"img/suggestion/mail_logo.png");
						model.put("footer",ApplicationConstants.getContext()+"img/suggestion/mail_footer.png");
						model.put("url", ApplicationConstants.getContext()+"subs/register?id="+authenticatedUser.getSubscriberId()+"&mail="+address+"&source=email");
						String mailContent = VelocityEngineUtils
								.mergeTemplateIntoString(getVelocityEngine(),
										"invitation.vm", "UTF-8", model);
						message.setText(mailContent, true);
	
					}
				};
				this.getMailSender().send(mimepreparator);
			}
		}
		if(addresses!=null && addresses.length>0){
			for(int i=0;i<addresses.length;i++){
				yazliste+=addresses[i];
			}
		}
		logger.info("appLog davet kullanici:" + userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " davetEdilen:" + yazliste);
		addActionMessage("Davetiyeler başarıyla gönderilmiştir.");
		return "success";
	}

	
	public String showInbox(){
		String result="success";
		String subscriberId=getServletRequest().getParameter(ApplicationConstants.SUBSCRIBER_ID);
		UserContext context=getUserContext();		
                if(context.getAuthenticatedUser()!=null){			
                    messageList=subscriberDAO.retrieveInboxMessagesBySubscriber(context.getAuthenticatedUser().getSubscriberId());		
                }
		return result;
	}
	
	public String showOutbox(){
		String result="success";
		String subscriberId=getServletRequest().getParameter(ApplicationConstants.SUBSCRIBER_ID);
		UserContext context=getUserContext();		
                if(context.getAuthenticatedUser()!=null){			
                    messageList=subscriberDAO.retrieveOutboxMessagesBySubscriber(context.getAuthenticatedUser().getSubscriberId());		
                }
		return result;
	}
	
	public String setRead(){
		String result="success";	
        subscriberDAO.setRead(messageId);
		return result;
	}
	
	public String deleteMessages(){
		String result="success";
		System.out.print(messageId);
        subscriberDAO.deleteMessages(messageId);
		return result;
	}

	public String retrieveFriendList(){
		String result="success";
		UserContext userContext=getUserContext();
		if(userContext.getAuthenticatedUser()!=null){
			friendList=subscriberDAO.retrieveFriendsBySubscriberId(userContext.getAuthenticatedUser().getSubscriberId());
		}
		
		return result;
	}
	
	public String register() {
		try{
			addStat(Integer.parseInt(id), (short)0, StatConstants.AT_REGISTRATION,StatConstants.IT_UNDEFINED, "email:"+mail+" source:"+source);
		}catch(Exception e){
			LOG.warn("register error", e);
		}
		
		return "success";
	}

	
	public SubscriberDAO getSubscriberDAO() {
		return subscriberDAO;
	}

	public void setSubscriberDAO(SubscriberDAO subscriberDAO) {
		this.subscriberDAO = subscriberDAO;
	}


	public String getMailAddressContainer() {
		return mailAddressContainer;
	}

	public void setMailAddressContainer(String mailAddressContainer) {
		this.mailAddressContainer = mailAddressContainer;
	}

	public List<TblMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<TblMessage> messageList) {
		this.messageList = messageList;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public List<TblSubscriber> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<TblSubscriber> friendList) {
		this.friendList = friendList;
	}

	public TblPoi getPoi() {
		return poi;
	}

	public void setPoi(TblPoi poi) {
		this.poi = poi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSt() {
		return st;
	}

	public void setSt(int st) {
		this.st = st;
	}





}
