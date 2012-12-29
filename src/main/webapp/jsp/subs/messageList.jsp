<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 
<div>
<s:form> 
<s:iterator value="%{#request.messageList}" status="messageStatus">
	<table>
		<tr>
			<s:url id="setRead" action="setRead?messageId" namespace="/subs"></s:url>
			<td><s:a href="%{setRead}"><s:property value="subject"/></s:a></td>
			<td><s:property value="sendDate"/> <br></td>
		</tr>
	</table>
</s:iterator>
</s:form>
</div>