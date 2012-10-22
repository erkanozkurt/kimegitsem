package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblRate;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;
import com.persona.kg.model.Subscriber;

public class UserAction extends BaseAction implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	public final static int RATE_LIKE = 1;
	public final static int RATE_DISLIKE = 2;
	private SubscriberDAO subscriberDAO;
	private String mailAddressContainer;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;

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
		if (mailAddressContainer != null) {
			String[] addresses=mailAddressContainer.split(",");
			for(int i=0;i<addresses.length;i++){
				final String address=addresses[i];
				MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage);
						// mail sending parameters
						message.setTo(address);
						message.setFrom("davet@kimegitsem.com");
						message.setSubject(authenticatedUser.getName()+" "+authenticatedUser.getSurname()+" sana kimegitsem?com’dan arkadaslik istegi gonderdi");
						Map model = new HashMap();
						model.put("name", authenticatedUser.getName());
						model.put("surname", authenticatedUser.getSurname());
						model.put("profile","https://graph.facebook.com/"+authenticatedUser.getFacebookId()+"/picture");
						model.put("logo",ApplicationConstants.getContext()+"img/kimegitsem.jpg");
						model.put("connect",ApplicationConstants.getContext()+"img/connect.jpg");
						model.put("context",ApplicationConstants.getContext());
						String mailContent = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"invitation.vm", "UTF-8", model);
						message.setText(mailContent, true);
	
					}
				};
				this.mailSender.send(mimepreparator);
			}
		}
		addActionMessage("Davetiyeler başarıyla gönderilmiştir.");
		return "success";
	}

	public SubscriberDAO getSubscriberDAO() {
		return subscriberDAO;
	}

	public void setSubscriberDAO(SubscriberDAO subscriberDAO) {
		this.subscriberDAO = subscriberDAO;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getMailAddressContainer() {
		return mailAddressContainer;
	}

	public void setMailAddressContainer(String mailAddressContainer) {
		this.mailAddressContainer = mailAddressContainer;
	}

}
