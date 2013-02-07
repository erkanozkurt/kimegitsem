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
    
	<div class="banner" style="width:576px; top:25px;">
		<img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/main.jpg")%>" />
	</div>

	<div class="nearLocations" id="nearLocations" style="top: 300px;">	
		<div class="grayHeading" style="width: 561px; top:90px;">Yakınınızdaki Yerel Hizmetler</div>
			<s:url var="url2publish" action="listPoi?categoryId=1" namespace="/ajax" />
			<s:url var="url3publish" action="listPoi?categoryId=2" namespace="/ajax" />
			<s:url var="url4publish" action="listPoi?categoryId=32" namespace="/ajax" />
			<s:url var="url5publish" action="listPoi?categoryId=25" namespace="/ajax" />
			<div class="categoryListInner" style="width: 561px; top: 90px;">
				<table>
					<tr>
						<td>
							<sj:div id="lastAdded" loadingText="Yükleniyor..." href="%{url2publish}" label="Son Eklenenler" cssClass="topcategory" cssStyle="width:230px;">İçerik Bulunmamaktadır.</sj:div>
						<td>
						<td>
							<sj:div id="lastAdded2" loadingText="Yükleniyor..." href="%{url3publish}" label="Son Eklenenler" cssClass="topcategory" cssStyle="width:230px;">İçerik Bulunmamaktadır.</sj:div>
						<td>
					</tr>
					<tr>
						<td>
							<sj:div id="lastAdded3" loadingText="Yükleniyor..." href="%{url4publish}" label="Son Eklenenler" cssClass="topcategory" cssStyle="width:230px;">İçerik Bulunmamaktadır.</sj:div>
						<td>
						<td>
							<sj:div id="lastAdded4" loadingText="Yükleniyor..." href="%{url5publish}" label="Son Eklenenler" cssClass="topcategory" cssStyle="width:230px;">İçerik Bulunmamaktadır.</sj:div>
						<td>
					</tr>
				</table>
			</div>
	</div>
	<div>
	
		<s:a action="profile" namespace="/subs" cssClass="invitefriends" style="top: 45px;" >Arkadaş Daveti</s:a>
	</div>
	<div > 
		<s:a action="claimForm" namespace="/in" cssClass="addPoi" style="top: 3px; left: 175px" >Hizmet Veren Ekle</s:a>
	</div>
</div>

<div class="rightcontentDiv">

	<div class="grayHeading" style="width: 205px; top:10px;">Son Katılan Hizmetverenler</div>
		<div class="categoryListInner" style="width: 205px; top: 8px;">
			<s:url var="lastAddedPoi" action="lastAddedPoi" namespace="/ajax" />
				<table>
					<tr>
						<sj:div id="lastAddedPoi" loadingText="Yükleniyor..." href="%{lastAddedPoi}" label="Son Eklenenler" cssClass="topcategory" cssStyle="width:230px;">İçerik Bulunmamaktadır.</sj:div>
					</tr>
				</table>
    	</div>
    	
    	<div class="grayHeading" style="width: 205px; top:10px;">Son Tavsiyeler</div>
		<div class="categoryListInner" style="width: 205px; top: 8px;">
			<s:url var="suggestion" action="lastSuggestion" namespace="/ajax" />
				<table>
					<tr>
						<sj:div id="lastSuggestion" loadingText="Yükleniyor..." href="%{suggestion}" label="Son Eklenenler" cssClass="topcategory" cssStyle="width:230px;">İçerik Bulunmamaktadır.</sj:div>
					</tr>
				</table>
    	</div>
    	
</div>
