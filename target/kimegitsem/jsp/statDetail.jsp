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
		<s:iterator value="stats" var="stat">
			<table width="300px" border="1" align="center">
				<tr>
					<td>Email</td>
					<td><s:property value="#stat[0]" /></td>
				</tr>
				<tr>
					<td>Yorum</td>
					<td><s:property value="#stat[1]" /></td>
				</tr>
			</table>
		</s:iterator>

	</div>

</body>
</html>