<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="contentDiv" id="contentDiv">
	<s:if test="#request.searchResult.size>0">	
			<table width="100%" border="0"  cellpadding="0" cellspacing="2"><tr>
				<s:iterator value="#request.searchResult" status="counter">
						<td valign="top"  width="50px">
							<s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><img border="0" src='<s:property value="profilePicture.thumbnail"/>' class="thumbnail" /></s:a>
						</td>
						<td valign="top">
							<s:a action="%{uniqueIdentifier}" namespace="/in" cssClass="profile"><s:property value="poiName"/></s:a>
						</td>
						<s:if test="!#counter.last && (#counter.index+1)%3==0">
					</tr>
					<tr>
					</s:if>
				</s:iterator>
				</tr>
			</table>
	</s:if>
</div>