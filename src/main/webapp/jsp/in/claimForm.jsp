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
			<td>İşletmenin adı</td>
			<td><s:textfield name="poi.poiName" id="poiName"></s:textfield></td>
		</tr>
		<tr>
			<td>Kategori</td>
			<td><select id="category" name="poi.category">
					<s:iterator value="categoryList">
						<option value='<s:property value="categoryId" />'><s:property value="categoryName" /></option>
					</s:iterator>
				</select></td>
		</tr>
		<tr>
			<td>Tanıtım metni</td>
			<td><s:textarea name="poi.info" id="info"></s:textarea></td>
		</tr>
		<tr>
			<td>Adres</td>
			<td><s:textfield name="poi.address" id="address"></s:textfield></td>
		</tr>
		<tr>
			<td>İl</td>
			<td>
				<select id="cityList" name="poi.cityId" onchange="populateDistricts()">
					<option value=''>Lütfen Seçiniz</option>
					<s:iterator value="cityList">
						<option value='<s:property value="cityId" />'><s:property value="cityName" /></option>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>İlçe</td>
			<td><select id="districtList" name="poi.districtId"></select></td>
		</tr>
		<tr>
			<td>Telefon</td>
			<td><s:textfield name="poi.phone" id="phone"></s:textfield></td>
		</tr>
		<tr>
			<td>Anahtar kelimeler</td>
			<td><s:textfield name="poi.keywords" id="keywords"></s:textfield></td>
		</tr>
		<tr>
			<td>Site</td>
			<td><s:textfield name="poi.website" id="website"></s:textfield></td>
		</tr>
		<tr>
			<td><a href="#" onclick="validatePoiInfo()"><img border="0" src="<%=response.encodeURL(request.getContextPath()+ "/img/save.jpg")%>"></a></td>
		</tr>
	</table>
	</s:form>
</div>

<script type="text/javascript">
function populateDistricts(){
	var cityComponent=document.getElementById("cityList");
	var cityId=cityComponent.options[cityComponent.selectedIndex].value;
	var districtComponent=document.getElementById("districtList");
	districtComponent.options.length = 0; 
	for(var i=0;i<districtList.length;i++){
		var item=districtList[i];
		if(item[0]==cityId){
			districtComponent.options[districtComponent.options.length] = new Option(item[2], item[1]);
		}
	}
}
function validatePoiInfo(){
	var poiName=document.getElementById("poiName").value;
	if(poiName==null || poiName=='undefined' || poiName.length<1){
		alert("Lüften geçerli bir işletme adı giriniz!");
		return false;
	}
	var info=document.getElementById("info").value;
	if(info==null || info=='undefined' || info<1){
		alert("Lüften geçerli bir tanıtım metni giriniz!");
		return false;
	}
	var address=document.getElementById("address").value;
	if(address==null || address=='undefined' || address<1){
		alert("Lüften geçerli bir adres giriniz!");
		return false;
	}
	var phone=document.getElementById("phone").value;
	if(phone==null || phone=='undefined' || phone<1){
		alert("Lüften geçerli bir telefon giriniz!");
		return false;
	}
	var keywords=document.getElementById("keywords").value;
	if(keywords==null || keywords=='undefined' || keywords<1){
		alert("Lütfen işletmenizi tanımlayan anahtar kelimeleri virgül ile ayrılmış şekilde giriniz!");
		return false;
	}
	var cityComponent=document.getElementById("cityList");
	var cityId=cityComponent.options[cityComponent.selectedIndex].value;
	if(cityId==''){
		alert("Lütfen bir şehir seçiniz!");
		return false;
	}
	document.getElementById("claimForm").submit();
}
</script>


<script type="text/javascript">
var districtList=new Array();
<s:iterator value="districtList"  status="counter">
districtList[<s:property value="#counter.index"/>]=new Array();
districtList[<s:property value="#counter.index"/>][0]=<s:property value="tblCity.cityId"/>;
districtList[<s:property value="#counter.index"/>][1]=<s:property value="districtId"/>;
districtList[<s:property value="#counter.index"/>][2]='<s:property value="districtName" escape="false"/>';
</s:iterator>
</script>