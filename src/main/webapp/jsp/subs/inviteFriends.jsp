<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>

<table width="300px" border="0">
	<tr>
		<td><input type="text" id="emailInput"></td>
		<td><a href="#" onclick="addToList()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/add.jpg")%>"></a></td>
	</tr>	
</table>

<table width="300px" border="0" id="mailListTable">
</table><br/>
<s:form action="sendInvitation" namespace="/subs" id="invitationForm"> 
	<input type="hidden" id="mailAddressContainer" name="mailAddressContainer">
	<a href="#" onclick="validateInvitationForm()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/send.jpg")%>"></a></a>
</s:form>
<s:actionmessage/>