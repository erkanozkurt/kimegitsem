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

import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.CategoryDAO;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.CachedResources;
import com.persona.kg.common.ImageResizer;
import com.persona.kg.common.ObjectIdGenerator;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblCity;
import com.persona.kg.dao.TblComment;
import com.persona.kg.dao.TblDistrict;
import com.persona.kg.dao.TblImage;
import com.persona.kg.dao.TblLandingPagePoi;
import com.persona.kg.dao.TblPoi;
import com.persona.kg.dao.TblPoiAdministrator;
import com.persona.kg.dao.TblPoiCategory;
import com.persona.kg.dao.TblPoiCategoryId;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;
import com.persona.kg.model.Subscriber;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

public class PoiAction extends BaseAction implements SessionAware {

	private SubscriberDAO subscriberDAO;
	private PoiDAO poiDAO;
	private CategoryDAO categoryDAO;
	private String userComment;
	private File imageFile;
	private TblCategory category;
	private List<TblPoi> poiList;
	private String categoryId;
	private TblPoi poi;
	private boolean updateMode=false;
	
	@Autowired
	private CachedResources cachedResources;
	
	public CachedResources getCachedResources() {
		return cachedResources;
	}
	public void setCachedResources(CachedResources cachedResources) {
		this.cachedResources = cachedResources;
	}
	public String show() {
		logger.debug("show invoked");
		return "show";
	}

	public String edit() {
		logger.debug("edit invoked");
		poi=getUserContext().getSelectedPoi();
		return "show";
	}

	public String postC() {
		logger.debug("postcomment invoked" + userComment);
		UserContext userContext = getUserContext();
		if (userContext.isLoggedIn()) {
			TblPoi poi = userContext.getSelectedPoi();
			TblSubscriber subscriber = subscriberDAO
					.retrieveFacebookSubscriber(userContext.getFacebookId());
			if (poi != null && subscriber != null) {
				String context = ServletActionContext.getRequest()
						.getRequestURI();
				context = context.replace("post/", "");
				String accessToken = userContext.getFacebookAccessToken();
				TblComment comment = new TblComment();
				comment.setComment(userComment);
				comment.setDateAdded(new Date());
				comment.setTblPoi(poi);
				comment.setTblSubscriber(subscriber);
				comment.setStatus((short)2);
				
				if (poiDAO.addComment(comment)) {
					poi.setComments(poiDAO.retrieveCommentsByPoi(poi));
					sendFacebookFeed(accessToken, context, poi.getPoiName());
				} else {
					// comment could not be added
				}

			}
		}
		return "show";
	}

	public String claim() {
		logger.debug("claim invoked");
		UserContext userContext = getUserContext();
		if (userContext.isLoggedIn()) {
			TblPoi selectedPoi=userContext.getSelectedPoi();
			if(selectedPoi!=null){
				if(selectedPoi.getAdministrator()==null){
					TblPoiAdministrator admin=new TblPoiAdministrator();
					admin.setTblSubscriber(userContext.getAuthenticatedUser());
					admin.setTblPoi(selectedPoi);
					admin.setIsPrimary(true);
					admin.setStatus((short)2);
					if(poiDAO.setAdmin(admin)){
						addActionMessage("Başvurunuz alınmıştır");
						selectedPoi.setAdministrator(userContext.getAuthenticatedUser());
					}
				}else{
					addActionMessage("Seçili hizmet veren için başvuru yapılmış!");
				}
			}else{
				addActionMessage("Seçili hizmet veren bulunamadı!");
			}
		}else{
			addActionMessage("Lütfen giriş yapınız!");
		}
		return "show";
	}
	
	public String addPoi() {
		logger.debug("claimForm invoked");
		UserContext userContext = getUserContext();
		if (userContext.isLoggedIn()) {
			if(updateMode==false){		
				poi.setUniqueIdentifier(escapeSpaces(poi.getPoiName()));
				poi.setDateAdded(new Date());
				if(poiDAO.addPoi(poi)){
					TblPoiCategory poiCategory=new TblPoiCategory();
					TblPoiCategoryId id=new TblPoiCategoryId();
					id.setPoiId(poi.getPoiId());
					id.setCategoryId(poi.getCategory());
					poiCategory.setId(id);
					poiDAO.addPoiCategory(poiCategory);
					poi.setAdministrator(userContext.getAuthenticatedUser());
					TblPoiAdministrator admin=new TblPoiAdministrator();
					admin.setTblSubscriber(userContext.getAuthenticatedUser());
					admin.setTblPoi(poi);
					admin.setIsPrimary(true);
					admin.setStatus((short)2);
					poiDAO.setAdmin(admin);
					addActionMessage("İşletme başarıyla kaydedildi!");
				}
			}else{
				if(poiDAO.updatePoi(poi)){
					TblPoiCategory poiCategory=new TblPoiCategory();
					TblPoiCategoryId id=new TblPoiCategoryId();
					id.setPoiId(poi.getPoiId());
					id.setCategoryId(poi.getCategory());
					poiCategory.setId(id);
					poiDAO.addPoiCategory(poiCategory);
				}
			}
		}else{
			addActionError("Lütfen giriş yapınız!");
		}
		return "show";
	}
	
	private void sendFacebookFeed(String accessToken, String context,
			String name) {
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		FacebookType publishResponse = facebookClient
				.publish(
						"me/feed",
						FacebookType.class,
						Parameter.with("message", userComment),
						Parameter
								.with("picture",
										ApplicationConstants.getContext()+"img/banner.jpg"),
						Parameter.with("link",
								ApplicationConstants.getDomainName() + context),
						Parameter.with("name", name), Parameter.with("caption",
								"kg"), Parameter.with("description", "kgtest"));
	}

	public String uploadImage() {
		logger.debug("uploadImage invoked" + userComment);
		UserContext userContext = getUserContext();
		if (getUploadFile() != null) {
			if (userContext.isLoggedIn()) {
				TblPoi poi = userContext.getSelectedPoi();
				TblSubscriber subscriber = subscriberDAO
						.retrieveFacebookSubscriber(userContext.getFacebookId());
				if (poi != null && subscriber != null) {
					logger.debug("file format:" + getUploadFileContentType());
					setUploadFileContentType(getUploadFileContentType()
							.toLowerCase());
					if (getUploadFileContentType().equalsIgnoreCase(
							"image/pjpeg")
							|| getUploadFileContentType().equalsIgnoreCase(
									"image/x-png")
							|| getUploadFileContentType().equalsIgnoreCase(
									"image/gif")
							|| getUploadFileContentType().equalsIgnoreCase(
									"image/jpeg")
							|| getUploadFileContentType().equalsIgnoreCase(
									"image/png")) {
						String fileName = "" + ObjectIdGenerator.getObjectId();
						String baseFileName = fileName;
						if (getUploadFileContentType().equalsIgnoreCase(
								"image/jpeg")
								|| getUploadFileContentType().equalsIgnoreCase(
										"image/pjpeg"))
							fileName += ".jpg";
						else if (getUploadFileContentType().equalsIgnoreCase(
								"image/png")
								|| getUploadFileContentType().equalsIgnoreCase(
										"image/x-png"))
							fileName += ".png";
						else
							fileName += ".gif";
						if (getUploadFile() != null
								&& getUploadFile().length() > 0
								&& getUploadFile().length() < 800000) {
							String outputFile = ApplicationConstants
									.getImageDir() + fileName;
							if (saveFileToFileSystem(outputFile)
									&& new ImageResizer(getUploadFile()
											.getAbsolutePath(),
											ApplicationConstants.getImageDir()
													+ "thumb_" + baseFileName
													+ ".png").createThumbnail()) {
								TblImage img = new TblImage();
								img.setImgType(poi.getPoiId());
								img.setTblSubscriber(subscriber);
								img.setStatus((short) 3);
								img.setFilename(fileName);
								poiDAO.addImage(img);
								poi.setImages(poiDAO.retrieveImagesByPoi(poi));
							} else {
								addActionMessage(getText("message.general.request.not.performed"));
							}
						} else {
							addActionError("Resim dosyasý izin verilenden daha büyük.");
						}
					} else {
						addActionError("Resim dosyasý formatý uygun deðil. Lütfen belirtilen resim formatlarýnda resimler yükleyin.");
					}
				}
			}
		}
		return "show";
	}

	public String listAjax() {
		logger.debug("postcomment invoked" + userComment);
		TblCategory category = categoryDAO.findCategoryById(categoryId);
		if (category != null) {
			this.setCategory(category);
			this.setPoiList(poiDAO.retrievePoisByCategory(category, 5));
		}
		return "poiListAjax";
	}
	
	public String addWatch(){
		String result="success";
		TblWatchList watch=new TblWatchList();
		String poiId=getServletRequest().getParameter(ApplicationConstants.POI_ID);
		UserContext context=getUserContext();
		if(poiId!=null && context.isLoggedIn()){
			watch.setDateAdded(new Date());
			watch.setItemId(Integer.parseInt(poiId));
			watch.setTblSubscriber(context.getAuthenticatedUser());
			watch.setItemType(ApplicationConstants.POI_TYPE);
			if(poiDAO.addWatch(watch)){
				context.putObject("poiWatchList", subscriberDAO.retrievePoiWatchListBysubscriberId(context.getAuthenticatedUser().getSubscriberId()));
			}
		}
		return result;
	}
	
	public String showSubscriberPoi(){
		String result="success";
		String subscriberId=getServletRequest().getParameter(ApplicationConstants.SUBSCRIBER_ID);
		UserContext context=getUserContext();
		if(subscriberId!=null){
			Integer intSubsId=Integer.parseInt(subscriberId);
			poiList=poiDAO.retrievePoisBySubscriber(intSubsId);
		}
		return result;
	}

	private String escapeSpaces(String source){
		if(source!=null){
			source=source.replaceAll("\\ ", "_");
		}
		return source;
	}
	
	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
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

	public TblPoi getPoi() {
		return poi;
	}

	public void setPoi(TblPoi poi) {
		this.poi = poi;
	}
	
	public List<TblCity> getCityList(){
		return cachedResources.getCityList();
	}
	
	public List<TblDistrict> getDistrictList(){
		return cachedResources.getDistrictList();
	}
	
	public List<TblCategory> getCategoryList(){
		return cachedResources.getCategoryList();
	}
	public boolean isUpdateMode() {
		return updateMode;
	}
	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

}
