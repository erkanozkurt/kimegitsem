<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="upper" id="upper">
	<div class="slogan" id="slogan"></div>
	<a href="<%=response.encodeURL(request.getContextPath()+ "/welcome")%>"><div class="logo" id="logo"></div></a>
	<div class="login" id="login">
		<fb:login-button autologoutlink="true"
			scope="email,user_likes,user_checkins,publish_stream"></fb:login-button>
	</div>
	
	<div class="login" id="logout" style="diplay:none">
		<a href="#" onclick="fblogout()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/logout.jpg")%>"></a>
	</div>
	
	<div class="searchArea" id="searchArea">
		<s:form labelposition="left" theme="simple" action="search"
			namespace="/s" id="searchHeaderform">
			<table>
				<tr>
					<td>
						<s:url id="categoryList" action="categoryList" namespace="/ajax"/>
					 	<sj:autocompleter id="whatBox" name="what" list="json" href="%{categoryList}" delay="50"  loadMinimumCount="2" placeholder="Ne" maxlength="10" onkeypress="return sendSearchForm(event);"/>
					</td>
					<td>
						<s:url id="placeList" action="placeList" namespace="/ajax"/>
						<sj:autocompleter id="where" list="json" name="where" onSelectTopics="whereSelected" href="%{placeList}" delay="50"  loadMinimumCount="2" placeholder="Nerede" maxlength="10" onkeypress="return sendSearchForm(event);"/>
					</td>
					<td><a href="#" onclick="javascript:document.getElementById('searchHeaderform').submit();"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/search.jpg")%>"></a>
					</td>
				</tr>
			</table>
		</s:form>	
		<div class="cityDistrict" id="cityDistrict"></div>
	</div>
</div>
<div class="menu" id="menu">
	<div class="menuInner" id="menuInner">
		<a href="#" onclick="return hs.htmlExpand(this, {width: 400, contentId: 'requestSuggestionPopup',wrapperClassName :'draggable-header'} )"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/sugreq.jpg")%>"></a> | 
		<a href="#" onclick="return hs.htmlExpand(this, {width: 400, contentId: 'suggestPopup',wrapperClassName :'draggable-header'} )"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/suggestmenu.jpg")%>"></a> | 
		<s:a action="list" namespace="/talk"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/talk.jpg")%>"></s:a>
	</div>
</div>

<div id="requestSuggestionPopup" class="popupDiv">			
		<div class="popupHeader">Tavsiye İstiyorum</div>
		<div >
			<s:form action="ask" method="post" namespace="/talk" id="askSuggestionForm">
				<table border="0">
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
</div>
<div id="suggestPopup" class="popupDiv">			
		<div class="popupHeader">Tavsiye Ediyorum</div>
		<div>
		
			<s:form action="suggest" method="post" namespace="/talk" id="suggestPlaceForm">
				<table border="0">
					<tr>
						<td>Neresi</td>
						<td>
							<s:url id="poiList" action="poiList" namespace="/ajax"/>
							<sj:autocompleter id="suggestion" list="json" name="suggestion" href="%{poiList}" delay="50"  loadMinimumCount="2" placeholder="Ne" maxlength="10" />
						</td> 
					</tr>
					<tr>
						<td align="center">
							<a href="#" onclick="return hs.close(this);"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/close.jpg")%>"></a>
							
						</td>
						<td align="center">	
							<a href="#" onclick="javascript:shareSuggestion(this);"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/suggest.jpg")%>"></a>
						</td>
					</tr>
				</table>
			</s:form>
			</div>
</div>


<div id="facebookLoginDiv" class="popupDiv">			
		<div class="popupHeader">kimegitsem?com'a bağlan!</div>
		<div>
			<fb:login-button autologoutlink="true"
				scope="email,user_likes,user_checkins,publish_stream"></fb:login-button>
		</div>
</div>

<script type="text/javascript">
	function sendSearchForm(e){
		if(e && e.keyCode == 13){
		      document.getElementById("searchHeaderform").submit();
		}
	}
	function shareSuggestion(sender){
		var suggestionValue=document.getElementById("suggestion").value;
		$.ajax({
			  url: "<%=response.encodeURL(request.getContextPath()+"/talk/suggest")%>?suggestion="+suggestionValue,
			  success:function (data) {
					   if(data!=null && data.indexOf("error")>-1){  
						   alert("İşlem sırasında hata oluştu!");
					   }else{
						   alert("Paylaşım başarıyla gerçekleşti");
					   }
					   hs.close("suggestPopup");
				   }
		});
		
	}
	
	$.subscribe('whereSelected', function(event, data) {
		alert(data);
	        });
</script>