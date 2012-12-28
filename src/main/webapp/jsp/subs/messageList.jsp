<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%> 
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<div class="messagelist">
<s:form> 
<s:iterator value="%{#request.messageList}" status="messageStatus">
	<table>
		<tr>
			<s:url id="setRead" action="setRead?messageId" namespace="/subs"></s:url>
			<td><s:a href="%{setRead}"><s:property value="subject"/></s:a></td>
			<td><s:property value="sendDate"/> <br></td>
		</tr>
	</table>
</s:iterator>
	<s:textarea id="message"></s:textarea>
</s:form>
</div>

 <sjg:grid
        id="gridtable"
        caption="Customer Examples"
        dataType="json"
        pager="true"
        gridModel="gridModel"
        rowList="10,15,20"
        rowNum="15"
        rownumbers="true"
    >
        <sjg:gridColumn name="id" index="id" title="ID" formatter="integer" sortable="false"/>
        <sjg:gridColumn name="name" index="name" title="Name" sortable="true"/>
        <sjg:gridColumn name="country" index="country" title="Country" sortable="false"/>
        <sjg:gridColumn name="city" index="city" title="City" sortable="false"/>
        <sjg:gridColumn name="creditLimit" index="creditLimit" title="Credit Limit" formatter="currency" sortable="false"/>
    </sjg:grid>


 <!-- 
<script type="text/javascript">
function setRead(){
	$.ajax({
		   
		   url: servletUrl+"/ajax/setRead?messageId="+'1', 
		   success: function (data) {
				if(data!=null){
					var messageComponent=document.getElementById("message");
					messageComponent.value=data.message;
				}
		   }
	}); 
}
</script>
 -->