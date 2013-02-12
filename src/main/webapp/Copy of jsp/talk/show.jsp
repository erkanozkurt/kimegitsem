<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="contentDiv" id="contentDiv">
	<s:if test="#request.conversation!=null">
		<s:property value="#request.conversation.subject"/>
		<s:property value="#request.conversation.description"/>
	</s:if>
</div>