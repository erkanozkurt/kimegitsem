<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%> 
<div class="messagelist">
<s:form> 

<s:iterator value="%{#request.messageList}" status="messageStatus">
	<table>
		<tr>
			<s:url id="setRead" action="setRead" namespace="/subs" >
				<s:param name="messageId" value="%{messageId}"></s:param>
			</s:url>
			<td><sj:a href="%{setRead}" targets="messageContent"><s:property value="subject"/></sj:a></td>
			<td><s:property value="sendDate"/> <br></td>
		</tr>
	</table>
</s:iterator>
	<s:textarea id="message"></s:textarea>
</s:form>
</div>

<div id="messageContent">
</div>
