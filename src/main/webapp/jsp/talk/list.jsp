<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="contentDiv" id="contentDiv">
	<s:if test="#request.convList.size>0">
	<div class="grayHeading" style="width: 500px">Konuşulanlar</div>			
			<table width="100%" border="0"  cellpadding="0" cellspacing="2">
				<tr style="font-weight: bold;">
					<td>Konu</td>
					<td>Kullanıcı</td>
					<td>Tarih</td>
				</tr>
				<s:iterator value="#request.convList" status="counter" var="conversation">
				<tr>
					<td><s:a action="show?talkId=%{#conversation[0]}"><s:property value="#conversation[1]"/></s:a></td>
					<td><s:a action="profile?profileId=%{#conversation[3]}" namespace="/subs" cssClass="profile" ><s:property value="#conversation[4]"/></s:a></td></td>
					<td><s:date name="#conversation[2]" format="dd.MM.yyyy hh:mm"/></td>
				</tr>	
				</s:iterator>
				</tr>
			</table>
	</s:if>
</div>