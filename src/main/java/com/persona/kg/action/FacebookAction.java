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

		UserContext context = (UserContext) request.getSession().getAttribute(
				ApplicationConstants.USER_CONTEXT_KEY);
		if (context.isLoggedIn() == false) {
			String accessToken = request.getParameter("access_token");
			context.setFacebookAccessToken(accessToken);
			FacebookClient facebookClient = new DefaultFacebookClient(
					accessToken);
			User facebookUser = retrieveUserDetails(facebookClient);

			if (facebookUser != null) {
				TblSubscriber subscriber = subscriberDao
						.retrieveFacebookSubscriber(facebookUser.getId());
				if (subscriber != null && subscriber.getActivated() == 1) {
					context.setLoggedIn(true);
					context.setFacebookId(facebookUser.getId());
					context.setAuthenticatedUser(subscriber);
					context.putObject(ApplicationConstants.FRIENDLIST_KEY, getSubscriberDao().retrieveFriendsBySubscriberId(subscriber.getSubscriberId()));
					putWatchList(context);
				} else {
					if (subscriber == null) {
						subscriber = new TblSubscriber();
						subscriber.setFacebookId(facebookUser.getId());
					}
					subscriber.setName(facebookUser.getFirstName());
					subscriber.setSurname(facebookUser.getLastName());
					subscriber.setJoinDate(new Date());
					subscriber.setGender(facebookUser.getGender().substring(0,1));
					subscriber.setActivated(1);

					if (subscriberDao.storeUser(subscriber)) {
						subscriber=subscriberDao.retrieveFacebookSubscriber(facebookUser.getId());
						context.setLoggedIn(true);
						context.setFacebookId(facebookUser.getId());
						context.setAuthenticatedUser(subscriber);
						//create friends
						populateFacebookFriends(facebookClient, subscriber.getSubscriberId());
						context.putObject(ApplicationConstants.FRIENDLIST_KEY, getSubscriberDao().retrieveFriendsBySubscriberId(subscriber.getSubscriberId()));
						getServletRequest().setAttribute("newMember", "true");
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

	private List<String> enumerateFriends(FacebookClient client) {
		List<String> result=new ArrayList<String>();
		Connection<User> myFriends = client.fetchConnection("me/friends",
				User.class);
		List<User> users = myFriends.getData();
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User usr = it.next();
			result.add(usr.getId());
		}
		return result;
	}

	private void populateFacebookFriends(FacebookClient client, int userId){
		List<String> facebookFriends=enumerateFriends(client);
		Iterator<String> iterator=facebookFriends.iterator();
		while(iterator.hasNext()){
			String friendId=iterator.next();
			TblSubscriber friend=subscriberDao.retrieveFacebookSubscriber(friendId);
			if(friend==null){
				friend=new TblSubscriber();
				friend.setActivated(0);
				friend.setFacebookId(friendId);
				friend.setJoinDate(new Date());
				subscriberDao.storeUser(friend);
				friend=subscriberDao.retrieveFacebookSubscriber(friendId);
			}
			if(friend.getSubscriberId()>0){
				subscriberDao.createFriend(userId, friend.getSubscriberId(),(short)1);
			}
		}
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