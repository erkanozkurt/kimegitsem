package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.persona.kg.CategoryDAO;
import com.persona.kg.LandingPageDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.SubscriberDAO;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.CachedResources;
import com.persona.kg.common.ImageResizer;
import com.persona.kg.common.JsonObject;
import com.persona.kg.common.ObjectIdGenerator;
import com.persona.kg.common.StatConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblCity;
import com.persona.kg.dao.TblComment;
import com.persona.kg.dao.TblConversation;
import com.persona.kg.dao.TblDistrict;
import com.persona.kg.dao.TblSubdistrict;
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
	private List<TblConversation> convList;
	private String categoryId;
	private TblPoi poi;
	private boolean updateMode = false;
	private int poiId;
	private List jsonList;
	private int districtId;
	private int cityId;
	private String authority;

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
		UserContext userContext = getUserContext();
		//addStat(getUserContext().getAuthenticatedUser().getSubscriberId(), userContext.getSelectedPoi().getPoiId() , StatConstants.AT_VIEW, StatConstants.IT_POI, "");
		if(userContext.getAuthenticatedUser()!=null){
		logger.info("appLog hizmGoster kullanici:" + 
		userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " hizmAd:" + userContext.getSelectedPoi().getPoiName());
		}else{
		logger.info("appLog hizmGoster kullanicisi : --- null --- "); 	
		}
		return "show";
	}

	public String edit() {
		logger.debug("edit invoked");
		poi = getUserContext().getSelectedPoi();
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
				comment.setStatus((short) 2);

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
			TblPoi selectedPoi = userContext.getSelectedPoi();
			if (selectedPoi != null) {
				if (selectedPoi.getAdministrator() == null) {
					TblPoiAdministrator admin = new TblPoiAdministrator();
					admin.setTblSubscriber(userContext.getAuthenticatedUser());
					admin.setTblPoi(selectedPoi);
					admin.setIsPrimary(true);
					admin.setStatus((short) 2);
					if (poiDAO.setAdmin(admin)) {
						addActionMessage("Başvurunuz alınmıştır");
						selectedPoi.setAdministrator(userContext
								.getAuthenticatedUser());
					}
				} else {
					addActionMessage("Seçili hizmet veren için başvuru yapılmış!");
				}
			} else {
				addActionMessage("Seçili hizmet veren bulunamadı!");
			}
		} else {
			addActionMessage("Lütfen giriş yapınız!");
		}
		return "show";
	}
	
	public String addPoi() {
		logger.debug("claimForm invoked");
		UserContext userContext = getUserContext();
		if (userContext.isLoggedIn()) {
			if (updateMode == false) {
				poi.setUniqueIdentifier(escapeSpace(poi.getPoiName()));
				poi.setDateAdded(new Date());
				if("1".equals(authority)){
					poi.setAuthorityEmail(userContext.getAuthenticatedUser().getEmail());
				}
				if (poiDAO.addPoi(poi)) {
					TblPoiCategory poiCategory = new TblPoiCategory();
					TblPoiCategoryId id = new TblPoiCategoryId();
					id.setPoiId(poi.getPoiId());
					id.setCategoryId(poi.getCategory());
					poiCategory.setId(id);
					
					
					poiDAO.addPoiCategory(poiCategory);
					poi.setAdministrator(userContext.getAuthenticatedUser());
					TblPoiAdministrator admin = new TblPoiAdministrator();
					admin.setTblSubscriber(userContext.getAuthenticatedUser());
					admin.setTblPoi(poi);
					admin.setIsPrimary(true);
					admin.setStatus((short) 2);
					poiDAO.setAdmin(admin);
					addActionMessage("İşletme başarıyla kaydedildi!");
					userContext.setSelectedPoi(poi);
					if(uploadFile!=null){
						poiId=poi.getPoiId();
						uploadLogo();
					}
					sendInfo();
					logger.info("appLog hizmEkle kullanici:" + userContext.getAuthenticatedUser().getName() + " " + userContext.getAuthenticatedUser().getSurname() + " hizmAd:" + poi.getPoiName() + " kategori:"+cachedResources.getCategoryMap().get(poi.getCategory()).getCategoryName());
									
				}
			} else {
				if (poiDAO.updatePoi(poi)) {
					TblPoiCategory poiCategory = new TblPoiCategory();
					TblPoiCategoryId id = new TblPoiCategoryId();
					id.setPoiId(poi.getPoiId());
					id.setCategoryId(poi.getCategory());
					poiCategory.setId(id);
					poiDAO.addPoiCategory(poiCategory);
				}
			}
		} else {
			addActionError("Lütfen giriş yapınız!");
		}
		return "show";
	}
	
	private String escapeSpace(String name) {
		if (name != null) {
			name=name.replaceAll("\\ ", "_");
			name=name.replaceAll("İ", "I");
			name=name.replaceAll("ı", "i");
			name=name.replaceAll("Ş", "S");
			name=name.replaceAll("ş", "s");
			name=name.replaceAll("Ç", "C");
			name=name.replaceAll("ç", "c");
			name=name.replaceAll("Ü", "U");
			name=name.replaceAll("ü", "u");
			name=name.replaceAll("Ö", "O");
			name=name.replaceAll("ö", "o");
			name=name.replaceAll("Ğ", "G");
			name=name.replaceAll("ğ", "g");
		}
		return name;
	}

	public String sendInfo() {
		final TblSubscriber authenticatedUser=getUserContext().getAuthenticatedUser();
		UserContext userContext = getUserContext();
				MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage);
						// mail sending parameters
						message.setTo("adnan.ertemel@gmail.com");
						message.setFrom("bilgi@kimegitsem.com");
						message.setSubject(authenticatedUser.getName()+" "+authenticatedUser.getSurname()+" bir hizmet veren ekledi.");
						Map model = new HashMap();
						model.put("name", authenticatedUser.getName());
						model.put("surname", authenticatedUser.getSurname());
						model.put("profile","https://graph.facebook.com/"+authenticatedUser.getFacebookId()+"/picture");
						model.put("logo",ApplicationConstants.getContext()+"img/suggestion/mail_logo.png");
						model.put("footer",ApplicationConstants.getContext()+"img/suggestion/mail_footer.png");
						
						model.put("poiname",poi.getPoiName());
						model.put("category",cachedResources.getCategoryMap().get(poi.getCategory()).getCategoryName());
						String mailContent = VelocityEngineUtils
								.mergeTemplateIntoString(getVelocityEngine(),
										"info.vm", "UTF-8", model);
						message.setText(mailContent, true);
	
					}
				};
				this.getMailSender().send(mimepreparator);
		return "success";
	}
	
	private void sendFacebookFeed(String accessToken, String context,
			String name) {
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		FacebookType publishResponse = facebookClient.publish(
				"me/feed",
				FacebookType.class,
				Parameter.with("message", userComment),
				Parameter.with("picture", ApplicationConstants.getContext()
						+ "img/banner.jpg"),
				Parameter.with("link", ApplicationConstants.getDomainName()
						+ context), Parameter.with("name", name),
				Parameter.with("caption", "kg"),
				Parameter.with("description", "kgtest"));
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

	public String uploadLogo() {
		UserContext userContext = getUserContext();
		if (getUploadFile() != null) {
			if (userContext.isLoggedIn()) {
				TblPoi poi = poiDAO.findPoiById(poiId);
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
								poiDAO.setPoiLogo(poi.getPoiId(),
										img.getFilename());
								poi.setProfileImage(img.getFilename());
								userContext.setSelectedPoi(poi);
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
			TblCategory cachedCategory = cachedResources.getCategoryMap().get(
					category.getCategoryId());
			if (cachedCategory != null) {
				String categoryList = cachedResources
						.getSubcategoryClause(cachedCategory);

				this.setPoiList( poiDAO.retrievePoisByCategoryWithSubcategories(
						categoryList, 5));
			}
		}
		return "poiListAjax";
	}
	
	public String lastAddedPoiList(){
		this.setPoiList( poiDAO.searchLastPoiByName(5));
		return "poiListAjax";
	}
	public String lastSuggestionList(){
		this.setConvList(poiDAO.searchLastSuggestion(5));
		return "poiListAjax";
	}
	public String addWatch() {
		String result = "success";
		TblWatchList watch = new TblWatchList();
		String poiId = getServletRequest().getParameter(
				ApplicationConstants.POI_ID);
		UserContext context = getUserContext();
		if (poiId != null && context.isLoggedIn()) {
			watch.setDateAdded(new Date());
			watch.setItemId(Integer.parseInt(poiId));
			watch.setTblSubscriber(context.getAuthenticatedUser());
			watch.setItemType(ApplicationConstants.POI_TYPE);
			if (poiDAO.addWatch(watch)) {
				context.putObject("poiWatchList", subscriberDAO
						.retrievePoiWatchListBysubscriberId(context
								.getAuthenticatedUser().getSubscriberId()));
			}
		}
		return result;
	}

	public String showSubscriberPoi() {
		String result = "success";
		String subscriberId = getServletRequest().getParameter(
				ApplicationConstants.SUBSCRIBER_ID);
		UserContext context = getUserContext();
		if (subscriberId != null) {
			Integer intSubsId = Integer.parseInt(subscriberId);
			poiList = poiDAO.retrievePoisBySubscriber(intSubsId);
		}
		return result;
	}

	public String retrieveDistrictList() {
		jsonList = poiDAO.retrieveDistrictListByCityId(cityId);
		return "success";
	}

	public String retrieveSubdistrictList() {
		jsonList = poiDAO.retrieveSubdistrictListByDistrictId(districtId);
		return "success";
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

	public List<TblCity> getCityList() {
		return cachedResources.getCityList();
	}

	public List<TblDistrict> getDistrictList() {
		return cachedResources.getDistrictList();
	}

	public List<TblSubdistrict> getSubdistrictList() {
		return cachedResources.getSubdistrictList();
	}

	public List<TblCategory> getCategoryList() {
		return cachedResources.getCategoryList();
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	public int getPoiId() {
		return poiId;
	}

	public void setPoiId(int poiId) {
		this.poiId = poiId;
	}

	public List getJsonList() {
		return jsonList;
	}

	public void setJsonList(List jsonList) {
		this.jsonList = jsonList;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public List<TblConversation> getConvList() {
		return convList;
	}

	public void setConvList(List<TblConversation> convList) {
		this.convList = convList;
	}

	
}
