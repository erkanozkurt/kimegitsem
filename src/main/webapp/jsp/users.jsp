<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="<s:url value='/css/main.css'/>" rel="stylesheet" type="text/css"/>
    <title><s:text name="label.users"/></title>
</head>
<body>
<div class="titleDiv"><s:text name="application.title"/></div>
<h1><s:text name="label.users"/></h1>
<s:url id="url" action="addUserInput" />
<a href="<s:property value="#url"/>">Add A New User</a>
<br/><br/>
<table class="borderAll">
    <tr>
        <th><s:text name="label.loginName"/></th>
        <th><s:text name="label.password"/></th>
        <th><s:text name="label.email"/></th>


    </tr>
    <s:iterator value="users" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td class="nowrap"><s:property value="loginname"/></td>
            <td class="nowrap"><s:property value="password"/></td>
            <td class="nowrap"><s:property value="emailAddress"/></td>
            <td class="nowrap">
                <s:url action="editUserInput" id="url">
                    <s:param name="user.Id" value="Id"/>
                </s:url>
                <a href="<s:property value="#url"/>">Edit</a>
                &nbsp;&nbsp;&nbsp;
                <s:url action="deleteUser" id="url">
                    <s:param name="user.Id" value="Id"/>
                </s:url>
                <a href="<s:property value="#url"/>">Delete</a>
            </td>
        </tr>
    </s:iterator>
    </table>
    
    <hr />
    <p><a href="<s:url action="index" namespace="config-browser" />">Launch the configuration browser</a></p>

</body>
</html>