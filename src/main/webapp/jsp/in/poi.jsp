<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

<!--
var selectedPoiId='<s:property value="#session.userContext.selectedPoi.poiId" />';
var selectedPoiUnique='<s:property value="#session.userContext.selectedPoi.uniqueIdentifier" />';
function suggest()
{
    FB.api(
      '/me/<%=ApplicationConstants.getProperty("facebookSuggest")%>',
      'post',
      { obje: '<%=ApplicationConstants.getContext()%>in/'+selectedPoiUnique },
      function(response) {
         if (!response || response.error) {
        	 debugger;
            alert('İstediğiniz işlem yapılamadı!');
            return false;
         } else {
            return true;
         }
      });
}
//-->
</script>

<div class="contentDiv" id="contentDiv">
	<s:actionmessage/>
	<s:if test="#session.userContext.selectedPoi">
		<!-- Poi detail start -->
		<table width="100%" border="0">
			<tr>
				<td width="*" valign="top">
					
					
					<table border="0" width="100%">
					    <tr>
							<td align="left">
							<s:if test="%{#session.userContext.selectedPoi.images}">
							<div class="highslide-gallery">
							
								<s:iterator value="%{#session.userContext.selectedPoi.images}" var="image" status="stat">
									<s:if test="#stat.index<3">								
										<a href="<s:property value="imageUrl"/>" class="highslide" onclick="return hs.expand(this, { slideshowGroup: 'first-group'})">
											<img border="0" src='<s:property value="thumbnail"/>' class="thumbnail" alt="Highslide JS" title="Büyütmek için tıklayın!"/>
										</a>
									</s:if>
									<s:if test="#stat.index==3">
										</div>
										<div class="hidden-container">
									</s:if>
									<s:if test="#stat.index>=3">
										<a href="<s:property value="imageUrl"/>" class="highslide" onclick="return hs.expand(this,{ slideshowGroup: 'first-group'})"></a>
									</s:if>
								</s:iterator>
							</div>
							</s:if>
							<br/>
							<s:a
									action="addPhoto/%{#session.userContext.selectedPoi.uniqueIdentifier}"
									namespace="/in" cssClass="link1" onclick="hs.htmlExpand(this, {width: 400, contentId: 'uploadImagePopup',wrapperClassName :'draggable-header'} );return false;">
								Fotoğraf Ekle
								</s:a>
							</td>
						</tr>
						<tr class="head1">
							<td><s:property value="#session.userContext.selectedPoi.poiName" /></td>
						</tr>
						<tr>
							<td valign="top"><span class="bold">Kategori: </span><s:iterator
									value="#session.userContext.selectedPoi.categories"
									var="categorie">
									<s:a action="filter?category=%{categoryId}" namespace="/s"
										cssClass="link1">
										<s:property value="categoryName" escape="true" />
									</s:a>&nbsp;
								</s:iterator>
							</td>
						</tr>
						<tr>
							<td valign="top"><span class="bold">Şehir/İlçe/Mahalle: </span><s:property
									value="#session.userContext.selectedPoi.cityId" escape="true" />
							</td>
						</tr>
						<tr>
							<td valign="top"><span class="bold">Adress: </span><s:property
									value="#session.userContext.selectedPoi.address" escape="true" />
							</td>
						</tr>
						<tr>
							<td valign="top"><span class="bold">Telefon: </span><s:property
									value="#session.userContext.selectedPoi.phone" escape="true" />
							</td>
						</tr>
						<s:if test="#session.userContext.selectedPoi.website!=null">
							<tr>
								<td valign="top"><span class="bold">Site: </span><s:property
										value="#session.userContext.selectedPoi.website" escape="true" />
								</td>
							</tr>
						</s:if>
						<s:if test="#session.userContext.selectedPoi.keywords!=null">
							<tr>
								<td valign="top"><span class="bold">Anahtar Kelimeler: </span> <s:property
										value="#session.userContext.selectedPoi.keywords" escape="true" />
								</td>
							</tr>
						</s:if>
						
						<tr>
							<td align="left">
								<s:form action="suggest/%{#session.userContext.selectedPoi.uniqueIdentifier}">
									<a href="#" onclick="suggest(); return false;"><img border="0"  src="<%=response.encodeURL(request.getContextPath()+ "/img/suggest.jpg")%>"></a>
									<s:if test="#session.userContext.getObject('poiWatchList').containsKey(#session.userContext.selectedPoi.poiId)==false">
										<a href="#" onclick="followPoi(this); return false;"><img border="0"  src="<%=response.encodeURL(request.getContextPath()+ "/img/follow.jpg")%>"></a>
									</s:if>
								</s:form>
								<div class="fb-like" data-href="<%=ApplicationConstants.getDomainName()+request.getContextPath()%>/in/<s:property value="%{#session.userContext.selectedPoi.uniqueIdentifier}"/>" data-send="false" data-layout="button_count" data-width="100" data-show-faces="true"></div>
							</td>
						</tr>
						<s:if test="#session.userContext.selectedPoi.administrator==null">
							<tr>
								<td>
									Burasının size ait olduğunu düşünüyorsanız hemen <s:a action="claim/%{#session.userContext.selectedPoi.uniqueIdentifier}" namespace="/in" cssClass="profile">Başvurun</s:a>						
								</td>
							</tr>
						</s:if>
						
						
						
					</table>

					</td>
					
					
				<td width="250px" valign="top">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><s:if test="#session.userContext.selectedPoi.coordLat">
									<script type="text/javascript"
										src="http://maps.googleapis.com/maps/api/js?key=<%=com.persona.kg.common.ApplicationConstants.getGoogleMapsKey()%>&sensor=true">
										
									</script>
									<script type="text/javascript">
										function initialize() {
											var lati = <s:property value="#session.userContext.selectedPoi.coordLat" escape="true" />;
											var longi = <s:property value="#session.userContext.selectedPoi.coordLong" escape="true" />;
											var poiLatlng = new google.maps.LatLng(
													lati, longi);
											var mapOptions = {
												center : poiLatlng,
												zoom : 16,
												mapTypeId : google.maps.MapTypeId.ROADMAP
											};
											var map = new google.maps.Map(
													document
															.getElementById("map_canvas"),
													mapOptions);
											var marker = new google.maps.Marker(
													{
														position : poiLatlng,
														map : map,
														title : "Hello World!"
													});

										}
									</script>
									<div id="map_canvas" style="width: 300px; height: 300px">
										<script type="text/javascript">
											initialize();
										</script>
									</div>
								</s:if> <s:else>
									<img border="0" 
										src="<%=response.encodeURL(request.getContextPath()
							+ "/img/nomap.jpg")%>"
										width="250" height="250" />
								</s:else>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			</table>
			<!-- Poi detail end -->
			
			
			<!-- Comments area start -->
			<table width="100%" border="0">
			<tr>
				<td>
					<s:if test="#session.userContext.loggedIn">
						<s:form action="post/%{#session.userContext.selectedPoi.uniqueIdentifier}" method="post" id="sendCommentForm">
									<s:textarea name="userComment" id="userComment" rows="2" cols="50" value="Tavsiye yaz..." onfocus="javascript:clearInput(this,'Tavsiye yaz...');" onblur="javascript:restoreInputValue(this,'Tavsiye yaz...');" cssStyle="width:100%;"></s:textarea>
							<tr>
								<td align="right" colspan="2">
									<a href="#" onclick="javascript:document.getElementById('sendCommentForm').submit();"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/send.jpg")%>"></a>
								</td>
							</tr>
						</table>
						</s:form>
					</s:if>
					<s:else>
						Yorum yapmak için lütfen giriş yapın  <fb:login-button autologoutlink="true"
			scope="email,user_likes,user_checkins,publish_stream"></fb:login-button>
					</s:else>
					
					
					<s:if test="#session.userContext.selectedPoi.comments.size>0">
						<div id="categroyHeader" class="grayHeading">Yorumlar</div>
						<div class="categoryListInner">
							<s:iterator value="#session.userContext.selectedPoi.comments" var="comment">
								
								<table width="100%" border="0">
									<tr>
										<td><s:a action="profile?profileId=%{tblSubscriber.subscriberId}" namespace="/subs" cssClass="profile" ><s:property value="tblSubscriber.displayName"/></s:a></td>
									</tr>
									<tr>
										<td><s:property value="comment"/></td>
									</tr>
									<tr>
										<td><div class="fb-like" data-href="<%=ApplicationConstants.getDomainName()+request.getContextPath()%>/in/comm<s:property value="commentId"/>/<s:property value="%{#session.userContext.selectedPoi.uniqueIdentifier}"/>" data-send="false" data-layout="button_count" data-width="100" data-show-faces="true"></div>
										<s:if test="#session.userContext.getObject('subscriberWatchList').containsKey(#session.userContext.authenticatedUser.subscriberId)==false">
											<a href="#" onclick="followSubscriber(this,'<s:property value="tblSubscriber.subscriberId"/>')"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/follow.jpg")%>"></a>
										</s:if>
										<a href="#" onclick='javascript:likeComment(<s:property value="commentId"/>)'><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/tup.jpg")%>"></a> <span class="tup_class"><s:property value="tupCount"/></span>
										<a href="#" onclick='javascript:dislikeComment(<s:property value="commentId"/>)'><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/tdown.jpg")%>"></a> <span class="tdown_class"><s:property value="tdownCount"/></span>
										</td>
									</tr>
									
								</table>
							</s:iterator>
						</div>
					</s:if>
				</td>
			</tr>
		</table>
		<!-- Comments area end -->
	</s:if>

	<s:else>
		Hizmetveren Bulunamadı
	</s:else>
	
	<!-- Utility popups area start -->
	<div id="uploadImagePopup" class="popupDiv">
		<div class="popupHeader">Resim Yükle</div>
		<div>
			<s:form action="uimg/%{#session.userContext.selectedPoi.uniqueIdentifier}" method="post" enctype="multipart/form-data" id="uploadPicForm">
				<s:file name="uploadFile">Yüklenecek fotoğraf</s:file>
				<table>
					<tr>
						<td>
							<a href="#" onclick="return hs.close(this);" ><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/close.jpg")%>"></a>
						</td>
						<td>
							<a href="#" onclick="javascript:document.getElementById('uploadPicForm').submit();"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/load.jpg")%>"></a>
						</td>
					</tr>
				</table>
				
			</s:form>
		</div>
	</div>
	
	<!-- Utility popups area end -->
	
	
</div>