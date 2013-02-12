<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table border="0" width="300px">
	<s:iterator value="%{#request.poiList}" status="poiStatus">
	  <tr>
	    <td width="50%"><s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><s:property value="poiName"/></s:a><br/></th>
	    <td width="50%"><s:a action="edit/%{uniqueIdentifier}" namespace="/in" cssClass="profile">Düzenle</s:a></td>
	  </tr>
	</s:iterator>
</table>
<s:a action="claimForm" namespace="/in" cssClass="profile">Yeni Ekle</s:a>