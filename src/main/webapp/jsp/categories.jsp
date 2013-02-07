<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div id="categoryList" class="categoryList">
	<div id="categroyHeader" class="grayHeading">Kategoriler</div>
	<div class="categoryListInner">
		<s:iterator value="#session.userContext.selectedCategory.parents" var="category" status="status"> 
			<s:a action="filter?category=%{categoryId}" namespace="/s" cssClass="category">
				<s:iterator begin="0"  end="%{#status.index}">&nbsp;</s:iterator>
				<s:property value="categoryName" escape="true" />
			</s:a>
			<br />
		</s:iterator>
	
		<s:iterator value="#session.userContext.selectedCategory.childs" var="category"> 
			<s:a action="filter?category=%{categoryId}" namespace="/s" cssClass="category">
				<s:property value="categoryName" escape="true" />
			</s:a>
			<br />
		</s:iterator>
	</div>
<div>
	<a href="#" onclick="addNewCategory()" class="offerCategory" style="position: relative; width:107px;">Kategori Öner</a>
</div>
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