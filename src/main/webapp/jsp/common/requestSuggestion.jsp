<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div width="100%" id="askSuggestionResult">
			<s:form action="ask" method="post" namespace="/talk" id="askSuggestionForm">
				<table border="0" width="100%">
					<tr>
						<td colspan="2">
							<s:url id="categoryListAs" action="categoryAll" namespace="/ajax"/>
					 		<sj:select labelposition="left"  label="Kategorisi" id="suggestedCategoryAs" name="suggestedCategory" list="json" href="%{categoryListAs}" delay="50"  loadMinimumCount="2" placeholder="Kategori" maxlength="-1"/>
						</td> 
					</tr>
					<tr>
						<td colspan="2">
							<s:url id="placeListAs" action="placeList" namespace="/ajax"/>
							<sj:autocompleter labelposition="left"  label="Yeri" id="suggestionPlaceAs" list="json" name="suggestionPlace" href="%{placeListAs}" delay="50"  loadMinimumCount="2" placeholder="Konum" maxlength="10" onkeypress="return sendSearchForm(event);"/>
						</td> 
					</tr>
					<tr>
						<td>Açıklama</td>
						<td>
							<s:textarea name="description"></s:textarea>
						</td> 
					</tr>
					<tr>
						<td colspan="2">Tavsiye İsteme Görünürlüğü:</td>
					</tr>
					<tr>
						<td valign="top"><input type="radio" name="suggestionVisibility" value="P" checked="checked"> Özel</td>
						<td width="100%" valign="top">
						<table border="0" width="100%">
						<s:if test="#session.userContext.getObject('friendList')!=null">
						<tr><td>
								Arkadaşlarım:</td><td> <s:select list="%{#session.userContext.getObject('friendList')}" name="selectedFriends" multiple="true" listKey="subscriberId" listValue="name" theme="simple"></s:select>
								</td>
						</s:if>
						<tr><td>
						E-mail listesi:</td><td><s:textfield name="emailList" theme="simple"></s:textfield></td></tr>
						</table>
						</td>
					</tr>
					<tr>
						<td><input type="radio" name="suggestionVisibility" value="A"> Yayınla (Facebook)</td>
						<td>					
						</td> 
					</tr>
					<tr>
						<td align="center">	
							<sj:submit value="Gönder" targets="askSuggestionResult"  src="/img/asksuggestion.jpg" type="image" onBeforeTopics="askSuggestion"></sj:submit>
						</td>
					</tr>
				</table>
			</s:form>
</div>
<script type="text/javascript">
	var poiSel=false;
	
	$.subscribe('askSuggestion', function(event, data) {
	
		 //event.originalEvent.options.submit = false;
		});
	function getValues(){
		alert("aaa");
		document.getElementById("categoryValue").value=document.getElementById("suggestedCategoryAs").value;
		document.getElementById("cityValue").value=document.getElementById("suggestedCityAs").value;
	}
	function getCategoryValue(){
		
		var category=document.getElementById("suggestedCategoryAs").value;
		return category;
		
		alert("aaa");
	}
	function getCityValue(){
		var city=document.getElementById("suggestedCityAs").value;
		return city;
		alert("aaa");
	}
</script>