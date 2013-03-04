package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;
import org.w3c.dom.Document;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.util.FileCopyUtils;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;
import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.model.Subscriber;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.sun.org.apache.xpath.internal.operations.Gte;

public class FacebookAction extends BaseAction implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";
	private SubscriberDAO subscriberDao;

	public SubscriberDAO getSubscriberDao() {
		return subscriberDao;
	}

	public void setSubscriberDao(SubscriberDAO subscriberDao) {
		this.subscriberDao = subscriberDao;
	}

	public String login() {
		logger.debug("access_token: " + request.getParameter("access_token"));
		logger.debug("ApplicationConstants.USER_CONTEXT_KEY: " + ApplicationConstants.USER_CONTEXT_KEY );
 
 
		UserContext context = (UserContext) request.getSession().getAttribute(
				ApplicationConstants.USER_CONTEXT_KEY);
	        
	       
	        logger.debug("context: " +context );
	        
	        logger.debug("context.isLoggedIn(): " +context.isLoggedIn() );
	        
				
		if (context.isLoggedIn() == false) {
			String accessToken = request.getParameter("access_token");
			context.setFacebookAccessToken(accessToken);
			FacebookClient facebookClient = new DefaultFacebookClient(
					accessToken);
			
			logger.debug("facebookClient : " + facebookClient);
			User facebookUser = retrieveUserDetails(facebookClient);

			if (facebookUser != null) {
				logger.debug("facebook user is not null!: "+facebookUser.getId());
				TblSubscriber subscriber = subscriberDao
						.retrieveFacebookSubscriber(facebookUser.getId());
				if (subscriber != null && subscriber.getActivated() == 1) {
					logger.debug("user exists: "+facebookUser.getId());
					context.setLoggedIn(true);
					context.setFacebookId(facebookUser.getId());
					context.setAuthenticatedUser(subscriber);
					context.putObject(ApplicationConstants.FRIENDLIST_KEY, getSubscriberDao().retrieveFriendsBySubscriberId(subscriber.getSubscriberId()));
					putWatchList(context);
				} else {
					logger.debug("user doest not exist: "+facebookUser.getId());
					if (subscriber == null) {
						subscriber = new TblSubscriber();
						subscriber.setFacebookId(facebookUser.getId());
					}
					logger.debug("creating user1: "+facebookUser.getId());
					subscriber.setName(facebookUser.getFirstName());
					subscriber.setSurname(facebookUser.getLastName());
					subscriber.setEmail(facebookUser.getEmail());
					logger.debug("creating user2: "+facebookUser.getId());
					subscriber.setJoinDate(new Date());
					subscriber.setGender(facebookUser.getGender().substring(0,1));
					subscriber.setActivated(1);
					logger.debug("creating user3: "+facebookUser.getId());
					if (subscriberDao.storeUser(subscriber)) {
						logger.debug("user stored: "+facebookUser.getId());
						subscriber=subscriberDao.retrieveFacebookSubscriber(facebookUser.getId());
						context.setLoggedIn(true);
						context.setFacebookId(facebookUser.getId());
						context.setAuthenticatedUser(subscriber);
						//create friends
						populateFacebookFriends(facebookClient, subscriber.getSubscriberId());
						logger.debug("friend list populated : "+facebookUser.getId());
						context.putObject(ApplicationConstants.FRIENDLIST_KEY, getSubscriberDao().retrieveFriendsBySubscriberId(subscriber.getSubscriberId()));
						getServletRequest().setAttribute("newMember", "true");
					}else{
						logger.debug("user could not be created: "+facebookUser.getId());
					}

				}
			}
		}
		return "success";
	}

	public String logout() {
		logger.debug("access_token: " + request.getParameter("access_token"));
		UserContext context = (UserContext) request.getSession().getAttribute(
				ApplicationConstants.USER_CONTEXT_KEY);
		context.setLoggedIn(false);
		
		return "success";
	}

	private List<User> enumerateFriends(FacebookClient client) {
		List<String[]> result=null;
		Connection<User> myFriends = client.fetchConnection("me/friends",
				User.class);
		List<User> users = myFriends.getData();
		return users;
	}

	private void populateFacebookFriends(FacebookClient client, int userId){
		logger.debug("populatig facebook friends: "+userId);
		List<User> facebookFriends=enumerateFriends(client);
		Iterator<User> iterator=facebookFriends.iterator();
		while(iterator.hasNext()){
			User friendStr=iterator.next();
			TblSubscriber friend=subscriberDao.retrieveFacebookSubscriber(friendStr.getId());
			if(friend==null){
				friend=new TblSubscriber();
				friend.setActivated(0);
				friend.setFacebookId(friendStr.getId());
				int surnameIndex=friendStr.getName().lastIndexOf(" ");
				friend.setName(friendStr.getName().substring(0,surnameIndex));
				friend.setSurname(friendStr.getName().substring(surnameIndex+1));
				friend.setJoinDate(new Date());
				subscriberDao.storeUser(friend);
				friend=subscriberDao.retrieveFacebookSubscriber(friendStr.getId());
			}
			if(friend.getSubscriberId()>0){
				subscriberDao.createFriend(userId, friend.getSubscriberId(),(short)1);
			}
		}
		logger.debug("populatig facebook friends: "+userId+" end");
	}


	private void putWatchList(UserContext userContext){
		TblSubscriber subscriber=userContext.getAuthenticatedUser();
		userContext.putObject("poiWatchList", subscriberDao.retrievePoiWatchListBysubscriberId(subscriber.getSubscriberId()));
		userContext.putObject("subscriberWatchList", subscriberDao.retrieveSubscriberWatchListBysubscriberId(subscriber.getSubscriberId()));
		userContext.putObject("talkWatchList", subscriberDao.retrieveTalkWatchListBysubscriberId(subscriber.getSubscriberId()));
	}

	private User retrieveUserDetails(FacebookClient client) {
		TblSubscriber subscriber = new TblSubscriber();
		User user = client.fetchObject("me", User.class);

		return user;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}
}
