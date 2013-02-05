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
			<s:url var="url2publish" action="listPoi?categoryId=100" namespace="/ajax" />
			<s:url var="url3publish" action="listPoi?categoryId=132" namespace="/ajax" />
			<s:url var="url4publish" action="listPoi?categoryId=109" namespace="/ajax" />
			<s:url var="url5publish" action="listPoi?categoryId=116" namespace="/ajax" />
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
	
		<s:a action="profilevb " namespace="/subs" cssClass="invitefriends" style="top: 45px;" >Arkadaş Daveti</s:a>
	</div>
	<div > 
		<s:a action="claimForm" namespace="/in" cssClass="addPoi" style="top: 3px; left: 175px" >Hizmet Veren Ekle</s:a>
	</div>
</div>
<div class="rightcontentDiv">

	<div class="redHeading2" style="width: 500px ; height: 25px; top:20px" align="center">
    </div>
  


</div>