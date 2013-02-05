<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 
<s:form namespace="/subs" theme="simple">
<s:submit action="delete" value="Sil" namespace="/subs" align="left" theme="simple"></s:submit> 
<div class="messageList">



	<table>

<s:iterator value="%{#request.messageList}" status="messageStatus" >

		<tr>
			<td>
				<s:checkbox name="messageIds" fieldValue="%{messageId}" theme="simple"></s:checkbox>
			</td>
			<td>
				<s:url id="setRead" action="setRead" namespace="/subs" >
					<s:param name="messageId" value="%{messageId}"></s:param>
				</s:url>
				<sj:a href="%{setRead}" targets="messageContent%{boxType}"><s:property value="subject"/></sj:a>

			</td>
			<td>
				<s:property value="sendDate"/> <br>
			</td>
		</tr>
	
</s:iterator>
</table>
</div>

	
</s:form>
<div id="messageContent<s:property value="%{boxType}"/>"></div>
