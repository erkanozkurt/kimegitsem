<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<style type="text/css">
body {
	background-image: url('/image/<s:text name="%{image}"/>');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
	text-align: center;
	margin: 0 auto;
}

#comment {
	width: 300px;
	position: absolute;
	top: 50%;
	left: 35%;
	background-color: white;
	filter: alpha(opacity =   80);
	-moz-opacity: 0.8;
	opacity: 0.8;
}
</style>
<script type="text/javascript">
	function validate_email(component) {

		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		var address = document.getElementById("email").value;
		if (reg.test(address) == false) {
			alert('Lütfen geçerli bir mail adresi giriniz!');
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div id="comment">
		<s:actionerror />
		<s:actionmessage cssStyle="color:green" />
		<s:if test="#session.subscriber!=null && #session.subscriber.context==#request.context">
		<div align="left">
			<s:text name="%{text}"></s:text>
		</div>
		<br />
			<s:form action="%{context}?save" method="post"
				onsubmit="return validate_email()">
				<s:textarea name="comment" label="%{getText('label.comment')}"
					rows="3"></s:textarea>
				<s:textfield name="email" label="%{getText('label.email')}"
					size="20" maxlength="100" id="email" />
				<s:submit value="%{getText('button.label.submit')}" />
			</s:form>
		</s:if>
		<s:else>
			<s:form action="%{context}?login" method="post"
				onsubmit="return validate_email()">
				<s:textfield name="username" label="%{getText('label.username')}"
					size="20" maxlength="100" id="username" />
				<s:password name="password" label="%{getText('label.password')}"
					size="20" maxlength="100" id="password" />
				<s:submit value="%{getText('button.label.submit')}" />
			</s:form>
		</s:else>
	</div>

</body>
</html>