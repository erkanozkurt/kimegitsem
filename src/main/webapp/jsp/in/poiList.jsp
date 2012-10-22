<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="redHeading"><s:property value="%{#request.category.categoryName}"/> </div>
<s:iterator value="%{#request.poiList}" status="poiStatus">
	<s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><s:property value="#poiStatus.count"/>. <s:property value="poiName"/></s:a><br/>
</s:iterator>