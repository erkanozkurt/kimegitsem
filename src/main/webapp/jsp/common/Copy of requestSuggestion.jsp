<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div style="width: 400px">
			<s:form action="ask" method="post" namespace="/talk" id="askSuggestionForm">
				<table border="0" width="400px">
					<tr>
						<td>Ne</td>
						<td>
							<s:url id="categoryList1" action="categoryList" namespace="/ajax"/>
							<sj:autocompleter id="whatBox2" list="json" name="what" href="%{categoryList1}" delay="50"  loadMinimumCount="2" placeholder="Ne" maxlength="10"/>
							
						</td> 
					</tr>
					<tr>
						<td>Nerede</td>
						<td>
							<s:url id="placeList1" action="placeList" namespace="/ajax"/>
							
							<sj:autocompleter id="whereBox2" list="json" name="where" href="%{placeList1}" delay="50"  loadMinimumCount="2" placeholder="Ne" maxlength="10"/>
						</td>
					</tr>
					<tr>
						<td>Özellikler</td>
						<td>
							<s:textarea rows="3" cols="30" name="description">
							</s:textarea>
						</td>
					</tr>
					<tr>
						<td align="center">
							<a href="#" onclick="return hs.close(this);"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/close.jpg")%>"></a>
							
						</td>
						<td align="center">	
							<a href="#" onclick="javascript:document.getElementById('askSuggestionForm').submit();"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/asksuggestion.jpg")%>"></a>
						</td>
					</tr>
				</table>
			</s:form>
</div>



<div width="100%" id="suggestResult">
			<s:form action="askSuggestionForm" method="post" namespace="/talk" id="askSuggestionForm">
				<table border="0" width="100%">
					<tr>
						<td colspan="2">
							<s:url id="categoryList2" action="categoryAll" namespace="/ajax"/>
					 		<sj:select labelposition="left"  label="Kategorisi" id="suggestedCategory" name="suggestedCategory" list="json" href="%{categoryList2}" delay="50"  loadMinimumCount="2" placeholder="Kategori" maxlength="-1"/>
						</td> 
					</tr>
					<tr>
						<td colspan="2">
							<s:url id="placeList2" action="placeList" namespace="/ajax"/>
							<sj:autocompleter labelposition="left"  label="Yeri" id="suggestionPlace" list="json" name="suggestionPlace" href="%{placeList2}" delay="50"  loadMinimumCount="2" placeholder="Konum" maxlength="10" onkeypress="return sendSearchForm(event);"/>
						</td> 
					</tr>
					<tr>
						<td>Açıklama</td>
						<td>
							<s:textarea name="description"></s:textarea>
						</td> 
					</tr>
					<tr>
						<td colspan="2">Tavsiye Görünürlüğü:</td>
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
							<sj:submit value="Gönder" targets="suggestResult"  src="/kimegitsem/img/suggest.jpg" type="image" onBeforeTopics="shareSuggestion"></sj:submit>
						</td>
					</tr>
				</table>
			</s:form>
</div>
<script type="text/javascript">
	var poiSel=false;
	$.subscribe('poiSelected', function(event, data) {
  	var ui = event.originalEvent.ui;
  	var message = ui.item.value;
  	if(ui.item.key) {
  		poiSel=true;
  		$.ajax({
			  url: "<%=response.encodeURL(request.getContextPath()+"/ajax/poiDetails")%>?poi.poiId="+ui.item.key,
			  success:function (data) {
					   if(data!=null){ 
						   var suggestedCategory=$('#suggestedCategory');
						   suggestedCategory.val(data.category);
						   suggestedCategory.attr('disabled','disabled');
						   var suggestedPlace=$('#suggestionPlace');
						   debugger;
						   suggestedPlace.attr('disabled','disabled');
						   
					   }
				   }
		});
  	}
	});
	
	$.subscribe('shareSuggestion', function(event, data) {
		alert("test");
		 event.originalEvent.options.submit = false;
		});
	
	
</script>