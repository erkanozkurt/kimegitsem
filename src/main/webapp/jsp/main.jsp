<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="contentDiv" id="contentDiv">

    <div align="center">
    <font face="Helvetica" size="6"><i>Arkadaş çevrenize danışmak hiç bu kadar kolay olmamıştı!</i></font>
    </div>

    <div class="blueHeading">
   				Kimegitsem ile hizmet sektöründe aradığınız her konuda güvenebileceğiniz hizmet verenleri arkadaşlarınızın tavsiyesi ile bulun.
    </div>
    
    <div class="redHeading2" style="width: 500px ; height: 25px; top:20px" align="center">
    	Kimegitsem'in temel fonksiyonları:
    </div>
    
	<div class="banner" style="width:576px; top:22px;">
		<img border="0" 
			src="<%=response.encodeURL(request.getContextPath()
					+ "/img/main.jpg")%>" />
	</div>
	<!--   
	<div>
		<a href="<s:url action="claimForm" namespace="/in"/>" class="addPoi" style="top: 43px;">Hizmetveren Ekle</a>		
	</div>
-->
	<div class="nearLocations" id="nearLocations" style="top: 300px;">
		
		<div class="grayHeading" style="width: 561px; top:90px;">Yakınınızdaki Yerel Hizmetler</div>
			<s:url var="url2publish" action="listPoi?categoryId=100" namespace="/ajax" />
			<s:url var="url3publish" action="listPoi?categoryId=132" namespace="/ajax" />
			<div class="categoryListInner" style="width: 561px; height: 130px; top: 90px;">
				<table>
					<tr>
						<td>
							<sj:div id="lastAdded" loadingText="Yükleniyor..." href="%{url2publish}" label="Son Eklenenler" cssStyle="width:230px; height:25px">İçerik Bulunmamaktadır.</sj:div>
						<td>
						<td>
							<sj:div id="lastAdded2" loadingText="Yükleniyor..." href="%{url3publish}" label="Son Eklenenler" cssStyle="width:230px; height:25px">İçerik Bulunmamaktadır.</sj:div>
						<td>
					</tr>
				</table>
			</div>
	</div>
</div>