<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>      
<s:actionmessage/>
<sj:tabbedpanel id="messagesPanel">
  		   <s:url id="inboxUrl" action="showInbox" namespace="/subs"></s:url>
		   <sj:tab id="profileTab" label="Gelen Kutusu" href="%{inboxUrl}" > </sj:tab>
	       
		   <s:url id="outboxUrl" action="showOutbox" namespace="/subs"></s:url>
		   <sj:tab id="profileTab" label="Giden Kutusu" href="%{outboxUrl}"> </sj:tab>
</sj:tabbedpanel>	
