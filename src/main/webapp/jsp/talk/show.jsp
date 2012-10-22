<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="contentDiv" id="contentDiv">
	<s:if test="#request.conversation!=null">
	<table width="100%">
		<tr>
			<td>
				<div class="grayHeading">
					<s:property value="#request.conversation.subject"/>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<s:property value="#request.conversation.body"/>
			</td>
		</tr>
		<tr>
			<td>
				<s:if test="#session.userContext.getObject('talkWatchList').containsKey(#request.conversation.conversationId)==false">
						<a href="#" onclick="followTalk(this,'<s:property value="#request.conversation.conversationId"/>'); return false;"><img border="0"  src="<%=response.encodeURL(request.getContextPath()+ "/img/follow.jpg")%>"></a>
				</s:if>
			</td>	
		</tr>
	</table>
	</s:if><br>
	<s:if test="#request.convReply.size>0">
	<table width="100%"><tr><td>
		<div id="categroyHeader" class="grayHeading">Cevaplar</div>
			<s:iterator value="#request.convReply" var="cReply">
				<table width="100%" border="0">
					<tr>
						<td><s:a action="profile?profileId=%{#cReply[3]}" namespace="/subs" cssClass="profile" ><s:property value="#cReply[4]"/></s:a></td>
						<td><s:property value="#cReply[2]" escape="true"/></td>
					</tr>
					<tr>
						<td><s:property value="#cReply[1]"/></td>
					</tr>
				</table>
			</s:iterator>
		</td>
		</tr>
	</table>
	</s:if><br><br>
	<s:form action="reply" namespace="/talk" id="replyForm">
			<s:hidden name="talkId" value="%{#request.conversation.conversationId}"></s:hidden>
				<table width="100%">
					<tr>
						<td>
							<div class="grayHeading">Cevap Yaz</div>
						</td>
					</tr>
					<tr>
						<td>
							<s:textarea name="replyText" theme="simple" cols="50" id="replyText"></s:textarea>
						</td>
					</tr>	
					<tr>
						<td>
							<a href="#" onclick="submitReply()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/send.jpg")%>"></a>
						</td>
					</tr>
				</table>
			</s:form>
</div>

<script type="text/javascript">
	function submitReply(){
		var replyText=document.getElementById("replyText");
		if(replyText==null || replyText=='undefined' || replyText==''){
			alert("Lütfen geçerli bir cevap giriniz!");
			return false;
		}
		document.getElementById("replyForm").submit();
		
		
		
	}
</script>