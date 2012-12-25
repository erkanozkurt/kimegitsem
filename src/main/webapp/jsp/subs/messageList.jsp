<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<s:iterator value="%{#request.messageList}" status="messageStatus">
	<s:property value="subject"/> <s:property value="sendDate"/><br/>
</s:iterator>