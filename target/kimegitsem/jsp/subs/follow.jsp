<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<s:if test="#session.userContext.getObject('subscriberWatchList').size>0">
<div class="redHeading">Takip ettiğim kullanıcılar</div>
	<s:iterator  value="#session.userContext.getObject('subscriberWatchList').values" var="watchItem">
		<s:a action="profile?profileId=%{#watchItem[0]}" namespace="/subs" cssClass="profile"><s:property value="#watchItem[1]"/></s:a><br>
	</s:iterator>
</s:if>

<s:if test="#session.userContext.getObject('poiWatchList').size>0">
<div class="redHeading">Takip ettiğim hizmetverenler</div>
	<s:iterator  value="#session.userContext.getObject('poiWatchList').values" var="watchItem">
		<s:a action="%{#watchItem[2]}" namespace="/in" cssClass="profile"><s:property value="#watchItem[1]"/></s:a><br>
	</s:iterator>
</s:if>

<s:if test="#session.userContext.getObject('talkWatchList').size>0">
<div class="redHeading">Takip ettiğim konuşulanlar</div>
<br/>
	<s:iterator  value="#session.userContext.getObject('talkWatchList').values" var="watchItem">
		<s:a action="%{#watchItem[2]}" namespace="/in" cssClass="profile"><s:property value="#watchItem[1]"/></s:a><br>
	</s:iterator>
</s:if>