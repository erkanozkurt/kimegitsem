<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="redHeading"><s:property value="%{#request.category.categoryName}"/> </div>
	<table width="100%" border="0"  cellpadding="0" cellspacing="2">
		<s:iterator value="%{#request.poiList}" status="poiStatus">
			<tr valign="top"  width="50px">
				<s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><img border="0" src='<s:property value="profilePicture.thumbnail"/>' class="thumbnail" /></s:a><br/>
			</tr>
			<tr valign="top">
				<s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><s:property value="#poiStatus.count"/>. <s:property value="poiName"/></s:a><br/>
			</tr>	
		</s:iterator>	
	</table>