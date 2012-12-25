<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="user==null || user.Id == null">
	<s:set name="title" value="%{'Add New User'}" />
</s:if>
<s:else>
	<s:set name="title" value="%{'Update User'}"/>
</s:else>

<html>
<head>
    <link href="<s:url value='/css/main.css'/>" rel="stylesheet" type="text/css"/>
    <style>td { white-space:nowrap; }</style>
    <title><s:property value="#title"/></title>
</head>
<body>
<div class="titleDiv"><s:text name="application.title"/></div>
<h1><s:property value="#title"/></h1>
<s:actionerror />
<s:actionmessage />
<s:form action="addUserSave" method="post">
    <s:textfield name="user.loginname"  label="%{getText('label.loginName')}" size="40"/>
    <s:textfield name="user.password"  label="%{getText('label.password')}" size="40"/>
    <s:textfield name="user.emailAddress" label="%{getText('label.email')}" size="40"/>
    <s:hidden name="user.Id" />
    <s:submit value="%{getText('button.label.submit')}"/>
    <s:submit value="%{getText('button.label.cancel')}" name="redirectAction:index"/>
</s:form>
<hr />

<p><a href="<s:url action="index" namespace="config-browser" />">Launch the configuration browser</a></p>
    
    
</body>
</html>