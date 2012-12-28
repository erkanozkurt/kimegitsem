<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="contentDiv" id="contentDiv">
<s:actionmessage/>

<s:form action="addPoi" namespace="/in" theme="simple" id="claimForm">
	<s:hidden name="poi.poiId"></s:hidden> 
	<s:hidden name="poi.uniqueIdentifier"></s:hidden> 
	<s:if test="#request.poi.uniqueIdentifier!=null">
		<s:hidden name="updateMode" value="true"></s:hidden>
	</s:if>
	<table width="100%">
		<tr>
			<td>Hizmetveren Adı<font color="red"> ***</font></td>
			<td><s:textfield name="poi.poiName" id="poiName" size="40"></s:textfield></td>
		</tr>
		<tr>
			<td valign="top">Bilgi</td>
			<td><s:textarea name="poi.info" id="info" cols="40" rows="10"></s:textarea></td>
		</tr>
		<tr>
			<td>Kategori<font color="red"> ***</font></td>
			<td><select id="category" name="poi.category">
					<option value=''>Lütfen Seçiniz</option>
					<s:iterator value="categoryList">
						<option value='<s:property value="categoryId" />'><s:property value="categoryName" /></option>
					</s:iterator>
				</select></td>
		</tr>
		<tr>
			<td>İl<font color="red"> ***</font></td>
			<td>
			
				<select id="cityList" name="poi.cityId" onchange="populateDistrictList()">
					<option value=''>Lütfen Seçiniz</option>
					<s:iterator value="cityList">
						<option value='<s:property value="cityId" />'><s:property value="cityName" /></option>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>İlçe</td>
			<!--   <td><select id="districtList" name="poi.districtId"></select></td> -->
			<td>
				<select id="districtList" name="poi.districtId" list="{'İlçe Seçiniz'}" onchange="populateSubdistrictList()" disabled="true">
					<option value=''>Lütfen Seçiniz</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Mahalle</td>
			<!--   <td><select id="districtList" name="poi.districtId"></select></td> -->
			<td>
				<select id="subdistrictList" name="poi.subdistrictId" list="{'Mahalle Seçiniz'}" disabled="true">
					<option value=''>Lütfen Seçiniz</option>
				</select>
			</td>
		</tr>
		<tr>
			<td valign="top">Adres</td>
			<td><s:textarea name="poi.address" id="address" cols="40" rows="3"></s:textarea></td>
		</tr>
		<tr>
			<td>Telefon</td>
			<td><s:textfield name="poi.phone" id="phone" size="30"></s:textfield></td>
		</tr>
		<tr>
			<td>Web Sitesi</td>
			<td><s:textfield name="poi.website" id="website" size="30"></s:textfield></td>
		</tr>
		<tr>
			<td>Anahtar Kelimeler</td>
			<td><s:textfield name="poi.keywords" id="keywords" size="30"></s:textfield></td>
		</tr>
		<tr>
			<tr>
				<td valign="top">Haritadaki Yeri</td>
			<tr>
			<td colspan="2"><script type="text/javascript"
										src="http://maps.googleapis.com/maps/api/js?key=<%=com.persona.kg.common.ApplicationConstants.getGoogleMapsKey()%>&sensor=true">
									</script>
									<script type="text/javascript">
										var oldMarker;
										var geocoder = null;
										function initialize() {
											var address = "Istanbul";
											geocoder = new google.maps.Geocoder();
											geocoder.geocode({ 'address': address }, function (results, status) {
												if (status == google.maps.GeocoderStatus.OK) {
													var latitude = results[0].geometry.location.lat();
													var longitude = results[0].geometry.location.lng();
													var poiLatlng = new google.maps.LatLng(latitude, longitude);
													var mapOptions = {
														center : poiLatlng,
														zoom : 11,
														mapTypeId : google.maps.MapTypeId.ROADMAP
													};
													var map = new google.maps.Map(document.getElementById("map_canvas"),mapOptions);
													
													google.maps.event.addListener(map, 'click', function(event) {
														var lat = event.latLng.lat();
														var lng = event.latLng.lng();
														document.getElementById("lat").value = lat; 
														document.getElementById("lng").value = lng; 
														placeMarker(event.latLng);
													});
													
													function placeMarker(location) {
														marker = new google.maps.Marker({
														position: location,
														map: map
														});
														if (oldMarker != undefined){
															oldMarker.setMap(null);
														}
														oldMarker = marker;
													}
												}
											}	);
										}
									</script>
									<div id="map_canvas" style="width: 500px; height: 400px">
										<script type="text/javascript">
											initialize();
										</script>
									</div>
						<s:hidden name="poi.coordLat" id="lat" />
						<s:hidden name="poi.coordLong" id="lng" />
			</td>
		</tr>
		<tr><td><u><font color="red"> *** </font><b>ile işaretlenmiş bilgilerin yazılması zorunludur.</b></u></td></tr>
		<tr>
			<td><a href="#" onclick="validatePoiInfo()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/save.jpg")%>"></a></td>
		</tr>
		
	</table>
	</s:form>
</div>

<script type="text/javascript">
<!--
function populateDistrictList(){
	var cityComponent=document.getElementById("cityList");
	var cityId=cityComponent.options[cityComponent.selectedIndex].value;
	retrieveDistrictList(cityId);
}


function retrieveDistrictList(cityId){
	$.ajax({
		   url: servletUrl+"/ajax/retrieveDistrictList?cityId="+cityId, 
		   success: function (data) {
				if(data!=null){
					var districtComponent=document.getElementById("districtList");
					districtComponent.options.length = 0;
					districtComponent.options[districtComponent.options.length] = new Option("Lütfen Seçiniz","");
					for(var i=0;i<data.length;i++){
								districtComponent.options[districtComponent.options.length] = new Option(data[i].districtName,data[i].districtId);
						}
					}  
					districtComponent.disabled=false;
				}
		   }
		); 
}

function populateSubdistrictList(){
	var districtComponent=document.getElementById("districtList");
	var districtId=districtComponent.options[districtComponent.selectedIndex].value;
	retrieveSubdistrictList(districtId);
}


function retrieveSubdistrictList(districtId){
	$.ajax({
		   url: servletUrl+"/ajax/retrieveSubdistrictList?districtId="+districtId, 
		   success: function (data) {
				if(data!=null){
					var subdistrictComponent=document.getElementById("subdistrictList");
					subdistrictComponent.options.length = 0;
					subdistrictComponent.options[subdistrictComponent.options.length] = new Option("Lütfen Seçiniz","");
					for(var i=0;i<data.length;i++){
						subdistrictComponent.options[subdistrictComponent.options.length] = new Option(data[i].subdistrictName,data[i].subdistrictId);
						}
					}  
					subdistrictComponent.disabled=false;
				}
		   }
		); 
}
function validatePoiInfo(){
	var poiName=document.getElementById("poiName").value;
	if(poiName==null || poiName=='undefined' || poiName.length<1){
		alert("Lüften geçerli bir işletme adı giriniz!");
		return false;
	}
	var categoryComponent=document.getElementById("category");
	var categoryId=categoryComponent.options[categoryComponent.selectedIndex].value;
	if(categoryId==''){
		alert("Lütfen bir kategori seçiniz!");
		return false;
	}
	var cityComponent=document.getElementById("cityList");
	var cityId=cityComponent.options[cityComponent.selectedIndex].value;
	if(cityId==''){
		alert("Lütfen bir şehir seçiniz!");
		return false;
	}
	var address=document.getElementById("address").value;
	if(address==null || address=='undefined' || address<1){
		alert("Lüften geçerli bir adres giriniz!");
		return false;
	}
	document.getElementById("claimForm").submit();
}
</script>