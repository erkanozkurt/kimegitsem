<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="redHeading"><s:property value="%{#request.category.categoryName}"/> </div>
<s:iterator value="%{#request.convList}" status="convStatus">
	<s:a action="%{conversationId}" namespace="/talk" cssClass="profile"><s:property value="#convStatus.count"/>. <s:property value="subject"/></s:a><br/>
</s:iterator>