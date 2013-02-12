<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:fb="http://www.facebook.com/2008/fbml">
<head prefix="og: http://ogp.me/ns# kgitsemtest: 
                  http://ogp.me/ns/apps/kgitsemtest#">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<sj:head />
<script type="text/javascript">
var servletUrl="<%=response.encodeURL(request.getContextPath())%>";

</script>
        
<title>kimegitsem?com - En iyisi, arkadaş tavsiyesi</title>
</head>
<body>ssss33aaa44kk
<s:url id="placeList" action="placeList" namespace="/ajax"/>
     <sj:autocompleter 
	    	id="where" 
	    	name="where"
	    	href="%{placeList}" 
	    	delay="50" 
	    	loadMinimumCount="2"
	    	placeholder="Ne" maxlength="10"
	    />

</body>
</html>
