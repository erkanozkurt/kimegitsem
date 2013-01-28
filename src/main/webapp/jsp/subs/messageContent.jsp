<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 
<s:form id="messageContent" action="sendMessage" method="post" namespace="/subs" >
	<s:hidden value="%{message.messageId}" name="message.mainMessageId"></s:hidden>
	<s:hidden value="%{message.tblSubscriberBySenderId.subscriberId}" name="message.tblSubscriberByRecipientId.subscriberId"></s:hidden>
	<s:hidden value="%{message.tblSubscriberByRecipientId.subscriberId}" name="message.tblSubscriberBySenderId.subscriberId"></s:hidden>
	<s:hidden value="%{message.subject}" name="message.subject"></s:hidden>
	<table>
	    <tr>
			<s:textarea value="%{message.message}" name="message.message" rows="5" cols="63"></s:textarea>
		</tr>
		<tr>
			<s:a href="#" onclick='showText()'>Cevap Yaz</s:a>
		</tr>
		<tr>
			<s:textarea id="messageText" value="%{message.message}" name="message.message" rows="5" cols="63" style="visibility: hidden;"></s:textarea>
		</tr>
		<tr>
			<s:submit id="sendMessage" value="Gönder" style="visibility: hidden;"></s:submit>
		</tr>		
	</table>
</s:form>

<script type="text/javascript">
	function showText(){
		document.getElementById("messageText").style.visibility="visible";
		document.getElementById("sendMessage").style.visibility="visible";
	}

</script>
