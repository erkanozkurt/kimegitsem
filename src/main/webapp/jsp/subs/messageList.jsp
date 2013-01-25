<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 

<div class="messageList">
<s:form namespace="/subs" > 

<s:submit action="delete" value="Sil" namespace="/subs" align="left"></s:submit>


<s:iterator value="%{#request.messageList}" status="messageStatus" >
	<table>
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
	</table>
</s:iterator>

	
</s:form>
</div>

<div id="messageContent<s:property value="%{boxType}"/>"></div>