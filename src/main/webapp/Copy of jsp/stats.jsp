<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
</head>
<body>
	<div id="comment">
		<s:actionerror />
		<s:actionmessage />
		<table width="300px" border="1" align="center">
		<tr><td>Tarih</td><td>İzleme</td><td>Yorum</td></tr>
		<s:iterator value="stats" var="stat">
			<tr><td><s:property value="#stat[2]"/></td><td><s:property value="#stat[1]"/></td><td><s:a action="%{context}?statDetail&date=%{#stat[2]}" >
			<s:property value="#stat[1]-#stat[0]"/></s:a></td></tr>
		</s:iterator>
		</table>
	</div>

</body>
</html>