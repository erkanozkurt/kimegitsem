<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="contentDiv" id="contentDiv">
    <div class="redHeading" style="width: 717px ; top: 250px;">
    Arkadaş çevrenize danışmak hiç bu kadar kolay olmamıştı !
    </div>
	<div class="banner">
		<img border="0" 
			src="<%=response.encodeURL(request.getContextPath()
					+ "/img/main.jpg")%>" />
	</div>
	<div class="nearLocations" id="nearLocations" style="top: 250px;">
		
		<div class="grayHeading" style="width: 500px">Yakınınızdaki Yerel Hizmetler</div>
			<s:url var="url2publish" action="listPoi?categoryId=100" namespace="/ajax" />
			<div class="categoryListInner" style="width: 500px">
					<sj:div id="lastAdded" loadingText="Yükleniyor..." href="%{url2publish}" label="Son Eklenenler" cssStyle="width:230px; height:25px">İçerik Bulunmamaktadır.</sj:div>
			</div>
			<br>
			<s:url var="url3publish" action="listPoi?categoryId=170" namespace="/ajax" />
			<div class="categoryListInner" style="width: 500px">
					<sj:div id="lastAdded" loadingText="Yükleniyor..." href="%{url3publish}" label="Son Eklenenler" cssStyle="width:230px; height:25px">İçerik Bulunmamaktadır.</sj:div>
			</div>
	</div>
</div>