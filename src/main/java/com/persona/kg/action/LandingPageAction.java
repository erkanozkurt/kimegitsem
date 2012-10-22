package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.model.Subscriber;

public class LandingPageAction extends BaseAction implements SessionAware {
	private String PATH = "C:/";
	private String context;
	private String text;
	private String image;
	private String email;
	private String comment;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private List<?> stats;
	private String date;
	private File poiImage;

	public File getPoiImage() {
		return poiImage;
	}

	public void setPoiImage(File poiImage) {
		this.poiImage = poiImage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private LandingPageDAO landingPageDao;
	private static final String SHOW = "show";
	private static final String EDIT = "edit";
	private static final String SAVE_EDIT = "saveEdit";
	private static final String STATS = "stats";
	private static final String LOGIN = "login";
	private static final String STAT_DETAIL = "statDetail";

	public LandingPageDAO getLandingPageDao() {
		return landingPageDao;
	}

	public void setLandingPageDao(LandingPageDAO landingPageDao) {
		this.landingPageDao = landingPageDao;
	}

	public String execute() {
		String requestedUrl = ServletActionContext.getRequest().getRequestURI();
		String remoteIp = ServletActionContext.getRequest().getRemoteHost();
		String result = SHOW;
		logger.debug("execute begin");
		if (requestedUrl != null && requestedUrl.indexOf("/") > -1) {
			requestedUrl = requestedUrl
					.substring(requestedUrl.lastIndexOf("/") + 1);
		}
		context = requestedUrl;
		boolean save = ServletActionContext.getRequest().getParameterMap()
				.containsKey("save");
		boolean edit = ServletActionContext.getRequest().getParameterMap()
				.containsKey("edit");
		boolean saveEdit = ServletActionContext.getRequest().getParameterMap()
				.containsKey(SAVE_EDIT);
		boolean stats = ServletActionContext.getRequest().getParameterMap()
				.containsKey(STATS);
		boolean statDetail = ServletActionContext.getRequest()
				.getParameterMap().containsKey(STAT_DETAIL);
		boolean login = ServletActionContext.getRequest().getParameterMap()
				.containsKey(LOGIN);
		if (save) {
			result = save(context, remoteIp);
		} else if (edit) {
			result = edit(context);
		} else if (saveEdit) {
			result = saveEdit(context);
		} else if (stats) {
			result = stats(context);
		} else if (statDetail) {
			result = statDetail(context);
		} else if (login) {
			result = login(context);
		} else
			result = showLandingPage(context, remoteIp);

		logger.debug("execute end");
		return result;
	}

	private String showLandingPage(String context, String remoteIp) {
		logger.debug("showLandingPage requestedUrl:" + context);
		landingPageDao.logTransaction(remoteIp, null, null, context, 1);
		TblLandingPagePoi findPoi = landingPageDao.findPoiByContext(context);
		if (findPoi != null) {
			image = findPoi.getImage();
			text = findPoi.getComment();
		}
		logger.debug("showLandingPage end");
		return SHOW;
	}

	private String save(String context, String remoteIp) {

		logger.debug("save requestedUrl:" + context + " comment:" + comment
				+ " email:" + email);
		landingPageDao.logTransaction(remoteIp, email, comment, context, 2);
		comment = "";
		email = "";
		TblLandingPagePoi findPoi = landingPageDao.findPoiByContext(context);
		if (findPoi != null) {
			image = findPoi.getImage();
			text = findPoi.getComment();
		}
		addActionMessage(getText("message.save"));
		logger.debug("save end requestedUrl:" + context);
		return SHOW;
	}

	private String edit(String context) {
		logger.debug("edit requestedUrl:" + context + " comment:" + comment
				+ " email:" + email);
		TblLandingPagePoi findPoi = landingPageDao.findPoiByContext(context);
		if (findPoi != null) {
			image = findPoi.getImage();
			text = findPoi.getComment();
		}
		logger.debug("save end requestedUrl:" + context);
		return EDIT;
	}

	private String login(String context) {
		logger.debug("login requestedUrl:" + context);
		TblLandingPagePoi findPoi = landingPageDao.findPoiByContext(context);
		if (findPoi != null) {
			image = findPoi.getImage();
			text = findPoi.getComment();
		}
		if (findPoi.getPassword() != null
				&& findPoi.getPassword().equals(password)
				&& findPoi.getUsername() != null
				&& findPoi.getUsername().equals(getUsername())) {
			Subscriber subscriber = new Subscriber();
			subscriber.setContext(context);
			ServletActionContext.getRequest().getSession()
					.setAttribute("subscriber", subscriber);
		}
		logger.debug("save end requestedUrl:" + context);
		return SHOW;
	}

	private String saveEdit(String context) {
		TblLandingPagePoi findPoi = landingPageDao.findPoiByContext(context);
		if (findPoi == null) {
			findPoi = new TblLandingPagePoi();
		}
		if (password != null)
			findPoi.setPassword(password);
		findPoi.setUsername(username);
		findPoi.setComment(text);
		if (poiImage != null) {
			String fileName = System.currentTimeMillis() + ".jpg";
			try {
				FileCopyUtils.copy(poiImage, new File(PATH + fileName));
				findPoi.setImage(fileName);
			} catch (IOException e) {
				addActionError("Resim kaydedilemedi");
				LOG.warn("Resim kaydedilemedi", e);
			}

		}

		findPoi.setPoiType(context);
		landingPageDao.savePoi(findPoi);
		logger.debug("save end requestedUrl:" + context);
		return EDIT;
	}

	private String stats(String context) {
		List<?> statList = landingPageDao.getDailyStats(context);
		stats = statList;
		logger.debug("save end requestedUrl:" + context);
		return STATS;
	}

	private String statDetail(String context) {
		List<?> statList = landingPageDao.getStatDetail(context, date);
		stats = statList;
		logger.debug("save end requestedUrl:" + context);
		return STAT_DETAIL;
	}

	public List<?> getStats() {
		return stats;
	}

	public void setStats(List<?> stats) {
		this.stats = stats;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPATH() {
		return PATH;
	}

	public void setPATH(String pATH) {
		PATH = pATH;
	}

}
