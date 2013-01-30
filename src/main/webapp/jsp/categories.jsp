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
<div>
	<a href="#" onclick="addNewCategory()" class="offerCategory" style="top: 377px; text-decoration: none;">Kategori Öner</a>
</div>
<s:form action="addCategory"  id="newCategoryForm" namespace="/subs">
	<s:hidden name="category.categoryName" id="categoryName2" value="category.categoryName"></s:hidden>
</s:form>
<script type="text/javascript">


	function addNewCategory(){
		var categoryName=prompt("Kategori adı","");
		document.getElementById("categoryName2").value=categoryName;
		document.getElementById("newCategoryForm").submit();
	}
	
</script>