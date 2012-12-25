<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div id="categoryList" class="categoryList">
	<div id="categroyHeader" class="grayHeading">Kategoriler</div>
	<div class="categoryListInner">
		<s:iterator value="#session.userContext.selectedCategory.childs" var="category"> 
			<s:a action="filter?category=%{categoryId}" namespace="/s" cssClass="category">
				<s:property value="categoryName" escape="true" />
			</s:a>
			<br />
		</s:iterator>
	</div>

</div>
