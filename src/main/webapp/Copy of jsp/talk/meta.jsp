<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="%{#request.conversation!=null}">
  		<meta property="fb:app_id" content="<%=ApplicationConstants.getFacebookApiKey()%>" /> 
  		<meta property="og:type" content="kgitsemtest:obje" /> 
  		<meta property="og:title" content="<s:property value="%{#request.conversation.subject}"/>"/> 
  		<meta property="og:description" content="<s:property value="%{#request.conversation.description}"/>"/> 
  		<meta property="og:url" content="<%=ApplicationConstants.getDomainName()+request.getContextPath()%>/talk/show?talkId=<s:property value="%{#request.conversation.conversationId}"/>"/>
</s:if>