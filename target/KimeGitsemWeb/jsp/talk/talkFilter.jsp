<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div id="categoryList" class="categoryList">
	<s:if test="#request.cityListKey.size>0">
		<div id="categroyHeader" class="grayHeading">Şehirlere göre
			konuşulanlar</div>
		<div class="categoryListInner">
			<s:iterator value="#request.cityListKey" var="city">
				<s:a action="list?cityId=%{#city[0]}" namespace="/talk"
					cssClass="category">
					<s:property value="#city[1]" escape="true" /> (<s:property
						value="#city[2]" escape="true" />)
			</s:a>
				<br />
			</s:iterator>
		</div>
	</s:if>
	<s:if test="#request.districtListKey.size>0">
		<div id="categroyHeader" class="grayHeading">İlçelere göre
			konuşulanlar</div>
		<div class="categoryListInner">
			<s:iterator value="#request.districtListKey" var="district">
				<s:a action="list?districtId=%{#district[0]}" namespace="/talk"
					cssClass="category">
					<s:property value="#district[1]" escape="true" /> (<s:property
						value="#district[2]" escape="true" />)
			</s:a>
				<br />
			</s:iterator>
		</div>
	</s:if>
</div>
