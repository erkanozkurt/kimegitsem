<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 
<script type="text/javascript">
document.getElementById("messageContent").style.visibility='hidden'; 
</script>
<s:form id="messageContent" action="sendMessage" method="post" namespace="/subs" >
	<s:hidden value="%{message.messageId}" name="message.mainMessageId"></s:hidden>
	<s:hidden value="%{message.tblSubscriberBySenderId.subscriberId}" name="message.tblSubscriberByRecipientId.subscriberId"></s:hidden>
	<s:hidden value="%{message.tblSubscriberByRecipientId.subscriberId}" name="message.tblSubscriberBySenderId.subscriberId"></s:hidden>
	<s:hidden value="%{message.subject}" name="message.subject"></s:hidden>
	<table>
	    <tr>
			<s:textarea value="%{message.message}" name="message.message" rows="5" cols="63" onclick="showText()"></s:textarea>
		</tr>
		<tr>
			<s:textarea id="messageContent" value="%{message.message}" name="message.message" rows="5" cols="63"></s:textarea>
		</tr>
		<tr>
		<s:submit id="sendMessage" value="Gönder" visible="false"></s:submit>
		</tr>
		
	</table>
</s:form>
<script type="text/javascript">
function showText(){
    alert("a");
    document.getElementById("messageContent").style.visibility='visible'; 
}
function hideText(){
    alert("a");
    document.getElementById("messageContent").style.visibility='hidden'; 
}


</script>
