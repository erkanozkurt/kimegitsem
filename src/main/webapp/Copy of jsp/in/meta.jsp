<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="%{#session.userContext.selectedPoi!=null}">
  		<meta property="fb:app_id" content="<%=ApplicationConstants.getFacebookApiKey()%>" /> 
  		<meta property="og:type" content="kgitsemtest:obje" /> 
  		<meta property="og:title" content="<s:property value="%{#session.userContext.selectedPoi.poiName}"/>"/> 
  		<meta property="og:image" content="<s:property value="%{#session.userContext.selectedPoi.images[1].thumbnail}"/>"/>
  		<meta property="og:description" content="<s:property value="%{#session.userContext.selectedPoi.description}"/>"/> 
  		<meta property="og:url" content="<%=ApplicationConstants.getDomainName()+request.getContextPath()%>/in/<s:property value="%{#session.userContext.selectedPoi.uniqueIdentifier}"/>"/>
</s:if>