package com.persona.kg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblSubscriber;
import com.persona.kg.dao.TblWatchList;

public class BaseAction  extends ActionSupport implements ServletRequestAware,SessionAware, ServletResponseAware
{
	public static String RESULT_SUCCESS="success";
	public static String RESULT_FAIL="fail";
	public static String RESULT_LOGIN="login";
	
	protected Log logger=LogFactory.getLog(BaseAction.class);
	protected File uploadFile;
	protected String uploadFileContentType;
	protected String uploadFileFileName;
	private String searchText;
	protected int searchCount;
	public String section; // hangi ekranda bulundugumuzu set ederiz. resim video v.b.
	public int rowSize=4;
	public boolean validationResult;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private CacheManager cacheManager;

	//protected final static String DESTINATION_DIR="/home/xv01d40337/tomcat5.5/webapps/content/";
	public static String DESTINATION_DIR="";//Configuration.destinationDir;
	public static String DIRECTORY_VIDEO=DESTINATION_DIR+"video/";
	public static String DIRECTORY_MUSIC=DESTINATION_DIR+"music/";
	public static String DIRECTORY_PICTURE=DESTINATION_DIR+"picture/";
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public boolean isValidationResult() {
		return validationResult;
	}
	public void setValidationResult(boolean validationResult) {
		this.validationResult = validationResult;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	public File getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	public boolean saveFileToFileSystem(String destinationFile)
	{
		logger.debug("saveFileToFileSystem [destinationFile]" +destinationFile);
		
		boolean isSaved=true;
		logger.debug("saving file to file system: "+destinationFile);
		FileOutputStream fos=null;
		FileInputStream fis=null;
		 try {
			 fos=new FileOutputStream(destinationFile);
			 fis=new FileInputStream(getUploadFile());
		      byte[] buffer = new byte[4096];
		      int bytesRead;
		      while ((bytesRead = fis.read(buffer)) != -1)
		        fos.write(buffer, 0, bytesRead); // write
		    } 
		 catch(Exception exp){
			 logger.debug("Exception while saving file : "  + exp.getMessage());
			 isSaved=false;
		 }
		 finally {
		      if (fis != null)
		        try {
		          fis.close();
		        } catch (IOException e) {
		          isSaved=false;;
		        }
		      if (fos != null)
		        try {
		          fos.close();
		        } catch (IOException e) {
		          isSaved=false;
		        }
		    }
		 logger.debug("saveFileToFileSystem end [isSaved]"+isSaved);
		    return isSaved;
	}

	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}

	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	public int getRowSize() {
		return rowSize;
	}
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
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
	public UserContext getUserContext(){
		UserContext userContext = (UserContext)ServletActionContext.getRequest().getSession()
		.getAttribute(ApplicationConstants.USER_CONTEXT_KEY);
		return userContext;
	}
	
	public String getApplicationContext(){
		String context=ApplicationConstants.getDomainName()+ServletActionContext.getRequest().getContextPath();
		return context;
	}
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	public Cache getCache(){
		return cacheManager.getCache("default");
	}
	
	
	protected String escapeSpaces(String source){
		if(source!=null){
			source=source.replaceAll("\\ ", "_");
		}
		return source;
	}
	
	public boolean sendMail(final Map attributes, final String template, final String email,final String subject) {
		
		boolean sendingResult=true;
		MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage);
						// mail sending parameters
						message.setTo(email);
						message.setSubject(subject);
						message.setFrom("ylcnarsln@gmail.com");
						String mailContent = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										template+".vm", "UTF-8", attributes);
						message.setText(mailContent, true);
	
					}
				};
		this.mailSender.send(mimepreparator);
		return sendingResult;
	}
	
	public String getMergedTemplate(String template, Map<String, String> attributes){
		String mailContent = VelocityEngineUtils
		.mergeTemplateIntoString(velocityEngine,
				template+".vm", "UTF-8", attributes);
		return mailContent;
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

	
}
