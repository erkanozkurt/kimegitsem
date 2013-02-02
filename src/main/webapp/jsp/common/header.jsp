<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="upper" id="upper">
	<!--  
	<div class="slogan" id="slogan"></div>
	-->
	<a href="<%=response.encodeURL(request.getContextPath()+ "/welcome")%>"><div class="logo" id="logo"></div></a>
	<div class="login" id="login">
		<fb:login-button autologoutlink="true"
			scope="email,user_likes,user_checkins,publish_stream">Facebook ile bağlan</fb:login-button>
	</div>
	<div class="login" id="logoutdiv" style="display:none">
		<a href="#" onclick="fblogout()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/logout.jpg")%>"></a>
	</div>
	<div id="userinfo" class="userinfo" style="visibility: visible;">
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
						<sj:autocompleter id="where" list="json" name="where" href="%{placeList}" delay="50"  loadMinimumCount="2" placeholder="Nerede" maxlength="10" onkeypress="return sendSearchForm(event);"/>
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
		<s:url var="requestSuggestionPopup" action="requestSuggestionPopup" namespace="/in"/> 
		<sj:a  href="%{requestSuggestionPopup}" openDialog="requestSuggestionPopupDialog" ><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/sugreq.jpg")%>"></sj:a> |
		<s:url var="showSuggestPopup" action="showSuggestPopup" namespace="/in"/> 
		<sj:a  href="%{showSuggestPopup}" openDialog="showSuggestPopupDialog" ><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/suggestmenu.jpg")%>"></sj:a> | 
		<s:a action="list" namespace="/talk"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/talk.jpg")%>"></s:a>
	</div>
</div>

<sj:dialog id="requestSuggestionPopupDialog" title="Tavsiye İstiyorum" autoOpen="false" modal="true"  width="500" position="center"></sj:dialog>
<sj:dialog id="showSuggestPopupDialog" title="Tavsiye Ediyorum" autoOpen="false" modal="true" width="500" position="center"></sj:dialog>

<div id="facebookLoginDiv" class="popupDiv">			
		<div class="popupHeader">kimegitsem?com'a bağlan!</div>
		<div align="center">
			<fb:login-button autologoutlink="true"
				scope="email,user_likes,user_checkins,publish_stream">Facebook ile bağlan</fb:login-button>
		</div>
</div>

<script type="text/javascript">
	function sendSearchForm(e){
		if(e && e.keyCode == 13){
		      document.getElementById("searchHeaderform").submit();
		}
	}

</script>