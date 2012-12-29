<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:fb="http://www.facebook.com/2008/fbml">
<head prefix="og: http://ogp.me/ns# kgitsemtest: 
                  http://ogp.me/ns/apps/kgitsemtest#">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<s:url action="profile" namespace="/subs" id="profileUrl"></s:url>
<s:url action="index" namespace="/" id="index"></s:url>
<s:head />
<sj:head />
<script type="text/javascript">
var servletUrl="<%=response.encodeURL(request.getContextPath())%>";
function serverLogin(accessCode){
		$.ajax({
			  url: "<%=response.encodeURL(request.getContextPath()+"/fb/login")%>?access_token="+accessCode,
			  success:function(data){
				  if(data!=null && data.indexOf("true")>-1){  
					   var profileUrl='<s:property value="profileUrl"/>';
					   window.location=profileUrl;
				   }else{
					   var indexPage='<s:property value="index"/>';
					   window.location=indexPage;
				   }
			  }
			});
			
}
</script>
        
<title>kimegitsem?com - En iyisi, arkadaş tavsiyesi</title>
</head>
<body>
	<div id="fb-root"></div>
	<script type="text/javascript">
		window.fbAsyncInit = function() {
			FB.init({
				appId : '<%=ApplicationConstants.getFacebookApiKey()%>',
				status : true,
				cookie : true,
				xfbml : true
			});
			FB.Event.subscribe('auth.login', function(response) {
				login(response);
			});
			FB.Event.subscribe('auth.logout', function(response) {
				logout();
			});

			FB.getLoginStatus(function(response) {
						if (response.status == 'connected') {
							login(response);
						}
					});
		};

		(function() {
			var e = document.createElement('script');
			e.type = 'text/javascript';
			e.src = document.location.protocol
					+ '//connect.facebook.net/tr_TR/all.js';
			e.async = true;
			document.getElementById('fb-root').appendChild(e);
		}());
		function login(response) {
			serverLogin(response.authResponse.accessToken);
			FB.api('/me', {fields: "id,name,picture"}, function(response) {
				var profileServlet="<%=response.encodeURL(request.getContextPath()+"/subs/profile") %>";
				var userInfo=document.getElementById("userinfo");
				var genericContent="<a href='"+profileServlet+"'><img border=\"0\"  src='"+response.picture.data.url+"' border='0'/></a><a href='"+profileServlet+"' class='profile'>"+response.name+"</a>";
				userInfo.innerHTML=genericContent;
				userInfo.style.visibility='visible';
				facebookId=response.id;
				loggedIn=true;
				});
		}
	</script>
	<center>
		<div>
			<img src="<%=response.encodeURL(request.getContextPath()+"/img/logo.jpg") %>" alt="" align="top" /><br/>
			<fb:login-button autologoutlink="true"
			scope="email,user_likes,user_checkins,publish_stream">Facebook ile hemen bağlan</fb:login-button>
		</div>
	</center>
</body>
</html>
