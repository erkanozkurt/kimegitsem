package com.persona.kg.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.context.ApplicationContext;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.persona.kg.CategoryDAO;
import com.persona.kg.PoiDAO;
import com.persona.kg.action.BaseAction;
import com.persona.kg.common.ApplicationConstants;
import com.persona.kg.common.UserContext;
import com.persona.kg.dao.TblCategory;
import com.persona.kg.dao.TblPoi;

public class PoiRetrieverInterceptor extends AbstractInterceptor implements
		StrutsStatics {

	private PoiDAO poiDAO;
	protected Log logger = LogFactory.getLog(CategoryInterceptor.class);

	public PoiDAO getPoiDAO() {
		return poiDAO;
	}

	public void setPoiDAO(PoiDAO poiDAO) {
		this.poiDAO = poiDAO;
	}

	@Override
	public String intercept(ActionInvocation action) throws Exception {
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		boolean debug = logger.isDebugEnabled();
		if (debug) {
			logger.debug("enter");
		}
		String context = ServletActionContext.getRequest().getRequestURI();
		if (context != null && context.indexOf("/") > -1) {
			context = context.substring(context.lastIndexOf("/") + 1);
		}
		if (debug) {
			logger.debug("selected context: " + context);
		}
		TblPoi selectedPoi = poiDAO.findPoiByContext(context);
		UserContext userContext = (UserContext) action
		.getInvocationContext().getSession()
		.get(ApplicationConstants.USER_CONTEXT_KEY);

		if (selectedPoi != null) {
			if (debug) {
				logger.debug("poi was found by context "
						+ selectedPoi.getPoiId());
			}
			selectedPoi.setCategories(poiDAO.retrieveCategoriesByPoi(selectedPoi));
			selectedPoi.setComments(poiDAO.retrieveCommentsByPoi(selectedPoi));
			selectedPoi.setImages(poiDAO.retrieveImagesByPoi(selectedPoi));
			selectedPoi.setAdministrator(poiDAO.retrievePoiAdministrator(selectedPoi.getPoiId()));
			userContext.setSelectedPoi(selectedPoi);
		} else {
			userContext.setSelectedPoi(null);
			if (debug) {
				logger.debug("poi could not be found");
			}
		}

		if (debug) {
			logger.debug("exit");
		}
		return action.invoke();
	}

}