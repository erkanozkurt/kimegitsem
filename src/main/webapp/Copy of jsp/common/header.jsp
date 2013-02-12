<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<div class="upper" id="upper">
	<div class="slogan" id="slogan"></div>
	<a href="<%=response.encodeURL(request.getContextPath()+ "/welcome")%>"><div class="logo" id="logo"></div></a>
	<div class="login" id="login">
		<fb:login-button autologoutlink="true"
			scope="email,user_likes,user_checkins,publish_stream"></fb:login-button>
	</div>
	<div class="searchArea" id="searchArea">
		<s:form labelposition="left" theme="simple" action="search"
			namespace="/s" id="searchHeaderform">
			<table>
				<tr>
					<td><s:url id="categoryList" action="categoryList" namespace="/ajax"/>
					<sx:autocompleter size="1"  name="what" id="whatBox"
							showDownArrow="false" cssClass="searchInput" value="Ne" href="%{categoryList}"
							searchType="substring" loadOnTextChange="true" loadMinimumCount="3" ></sx:autocompleter>
					</td>
					<td><s:url id="placeList" action="placeList" namespace="/ajax"/>
					<sx:autocompleter name="where" id="whereBox"
							showDownArrow="false" cssClass="searchInput" value="Nerede" href="%{placeList}"
							searchType="substring" loadOnTextChange="true"  loadMinimumCount="3" ></sx:autocompleter>
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
							<sx:autocompleter name="what" id="whatBox2"
								showDownArrow="false" cssClass="searchInput" value="" href="%{categoryList1}"
								searchType="substring" loadOnTextChange="true"  loadMinimumCount="3"></sx:autocompleter>
						</td> 
					</tr>
					<tr>
						<td>Nerede</td>
						<td>
							<s:url id="placeList1" action="placeList" namespace="/ajax"/>
							<sx:autocompleter name="where" id="whereBox2"
								showDownArrow="false" cssClass="searchInput" value="" href="%{placeList1}"
								searchType="substring" loadOnTextChange="true"  loadMinimumCount="3" ></sx:autocompleter>
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
			<s:form action="ask" method="post" namespace="/talk" id="suggestPlaceForm">
				<table border="0">
					<tr>
						<td>Neresi</td>
						<td>
							<s:url id="poiList" action="poiList" namespace="/ajax"/>
							<sx:autocompleter name="suggestion" id="suggestion"
								showDownArrow="false" cssClass="searchInput" value="" href="%{poiList}"
								searchType="substring" loadOnTextChange="true"  loadMinimumCount="3"></sx:autocompleter>
						</td> 
					</tr>
					<tr>
						<td align="center">
							<a href="#" onclick="return hs.close(this);"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/close.jpg")%>"></a>
							
						</td>
						<td align="center">	
							<a href="#" onclick="javascript:document.getElementById('suggestPlaceForm').submit();"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/suggest.jpg")%>"></a>
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

function bindOnChangeListener(){
	var whatBox=dojo.widget.byId("whatBox");
	var whereBox=dojo.widget.byId("whereBox");
	var textInputNodeWhat=whatBox.textInputNode;
	var textInputNodeWhere=whereBox.textInputNode;
	textInputNodeWhat.defValue="Ne";
	dojo.event.connect(textInputNodeWhat,"onfocus",clearDojoInput);
	dojo.event.connect(textInputNodeWhat,"onblur",restoreDojoInputValue);
	textInputNodeWhere.defValue="Nerede";
	dojo.event.connect(textInputNodeWhere,"onfocus",clearDojoInput);
	dojo.event.connect(textInputNodeWhere,"onblur",restoreDojoInputValue);
}
dojo.addOnLoad(bindOnChangeListener);
</script>