<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="contentDiv" id="contentDiv">

    <div class="redHeading2" style="width: 561px ; top: 250px; height: 25px; top:10px" align="center">
    Arkadaş çevrenize danışmak hiç bu kadar kolay olmamıştı !
    </div>

    <div class="blueHeading" style="width: 561px; height: 70px;  top:30px" align="center">Kimegitsem ile hizmet sektöründe aradığınız her konuda güvenebileceğiniz hizmet verenleri
                                                  arkadaşlarınızın tavsiyesi ile bulun.
    </div>
    
    <div class="redHeading2" style="width: 561px ; height: 25px; top:50px" align="center">
    	Kimegitsem'de kullanabileceğiniz temel özellikler:
    </div>
    
	<div class="banner" style="width:576px; top:52px;">
		<img border="0" 
			src="<%=response.encodeURL(request.getContextPath()
					+ "/img/main.jpg")%>" />
	</div>
	
	<div class="nearLocations" id="nearLocations" style="top: 300px;">
		
		<div class="grayHeading" style="width: 561px; top:90px;">Yakınınızdaki Yerel Hizmetler</div>
			<s:url var="url2publish" action="listPoi?categoryId=100" namespace="/ajax" />
			<div class="categoryListInner" style="width: 561px; height: 130px; top: 88px;">
					<sj:div id="lastAdded" loadingText="Yükleniyor..." href="%{url2publish}" label="Son Eklenenler" cssStyle="width:230px; height:25px">İçerik Bulunmamaktadır.</sj:div>
			</div>
	</div>
</div>