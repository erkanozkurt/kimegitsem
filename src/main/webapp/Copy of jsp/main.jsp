<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<div class="contentDiv" id="contentDiv">
	<div class="banner">
		<img border="0" 
			src="<%=response.encodeURL(request.getContextPath()
					+ "/img/banner.jpg")%>" />
	</div>
	<div class="nearLocations" id="nearLocations" style="top: 250px;">
		
		<div class="grayHeading" style="width: 500px">Yakınınızdaki
			Yerel Hizmetler</div>
		<s:url var="url2publish" action="listPoi?categoryId=4" namespace="/ajax" />
		<div class="categoryListInner" style="width: 500px">
				<sx:div id="lastAdded" loadingText="Yükleniyor..."
					label="Son Eklenenler" href="%{url2publish}"
					theme="ajax" refreshOnShow="false" executeScripts="true"
					autoStart="false" cssStyle="width:230px">İçerik Bulunmamaktadır.			
		</sx:div>
		
		</div>
		
	</div>
</div>