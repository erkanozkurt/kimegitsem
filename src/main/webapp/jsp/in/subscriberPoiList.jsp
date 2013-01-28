<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table border="0" width="300px">
	<s:iterator value="%{#request.poiList}" status="poiStatus">
	  <tr>
	    <td width="34%"><s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><s:property value="poiName"/></s:a><br/></th>
	    <td width="33%"><s:a action="edit/%{uniqueIdentifier}" namespace="/in" cssClass="profile">Düzenle</s:a></td>
	    <td width="33%"><a href="#" class="profile" onclick="document.getElementById('poiId').value=<s:property value="poiId"/>; hs.htmlExpand(this, {width: 400, contentId: 'uploadImagePopup',wrapperClassName :'draggable-header'} );return false;">Logo Yükle</a></td>
	  </tr>
	</s:iterator>
</table>
<!-- Utility popups area start -->
	<div id="uploadImagePopup" class="popupDiv">
		<div class="popupHeader">Logo Yükle</div>
		<div>
			<s:form action="ulogo" method="post" enctype="multipart/form-data" id="uploadPicForm" namespace="/in">
				<s:file name="uploadFile">Yüklenecek fotoğraf</s:file>
				<s:hidden id="poiId" name="poiId"></s:hidden>
				<table>
					<tr>
						<td>
							<a href="#" onclick="return hs.close('uploadImagePopup');" ><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/close.jpg")%>"></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#" onclick="javascript:document.getElementById('uploadPicForm').submit();"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/load.jpg")%>"></a>
						</td>
					</tr>
				</table>
				
			</s:form>
		</div>
	</div>
<s:a action="claimForm" namespace="/in" cssClass="profile">Yeni Ekle</s:a>