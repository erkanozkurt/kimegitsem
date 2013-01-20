<%@page import="com.persona.kg.common.ApplicationConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:fb="http://www.facebook.com/2008/fbml">
<head prefix="og: http://ogp.me/ns# kimegitsem: 
                  http://ogp.me/ns/apps/kimegitsem#">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<%if(request.getAttribute("facebookClient").equals("true")){ %>
	<tiles:insertAttribute name="meta" />
<%}%>
<s:head/>
<sj:head  compressed="false" jqueryui="true"/>

<s:url action="profile" namespace="/subs" id="profileUrl"></s:url>

<script type="text/javascript"
        src="<%=response.encodeURL(request.getContextPath()+"/js/kg.js")%>"></script>

<script type="text/javascript"
        src="<%=response.encodeURL(request.getContextPath()+"/js/highslide-full.packed.js")%>"></script>

<title>kimegitsem?com - En iyisi, arkadaş tavsiyesi</title>
<link href="<%=response.encodeURL(request.getContextPath()+"/css/style.css?v=3") %>" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+"/css/highslide.css")%>" />
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-37475737-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body>
<%if(request.getAttribute("facebookClient").equals("false")){ %>
	<div id="fb-root"></div>
	<script type="text/javascript">
		var index = 1;
		var servletUrl="<%=response.encodeURL(request.getContextPath())%>";			
	</script>
	<script type="text/javascript">
		
		function serverLogin(accessCode){	
			$.ajax({
				  url: "<%=response.encodeURL(request.getContextPath()+"/fb/login")%>?access_token="+accessCode,
				  success:function (data) {
						   if(popupMode==true){
							   hs.close("facebookLoginDiv");
							   popupMode=false;
						   }
						   
						   if(data!=null && data.indexOf("true")>-1){  
							   var profileUrl='<s:property value="profileUrl"/>';
							   window.location=profileUrl;
						   }
					   }
			});
		}
		
		function serverLogout(){
			$.ajax({
					   url: "<%=response.encodeURL(request.getContextPath()+"/fb/logout")%>", 
					 }); 
		}
		window.fbAsyncInit = function() {
			FB.init({
				appId : '<%=ApplicationConstants.getFacebookApiKey()%>',
				status : true,
				cookie : true,
				xfbml : true
			});
			FB.Event.subscribe('auth.login', function(response) {
				login(response);
				document.getElementById('login').style.display='none';
				document.getElementById('logoutdiv').style.display = 'block';
				try{
					hs.close();
				}catch(error){
				alert(error);
				}
			});
			FB.Event.subscribe('auth.logout', function(response) {
				logout();
				document.getElementById('login').style.display='block';
				document.getElementById('logoutdiv').style.display = 'hide';
			});

			FB.getLoginStatus(function(response) {
						if (response.status == 'connected') {
							login(response);
							document.getElementById('login').style.display='none';
							document.getElementById('logoutdiv').style.display = 'block';
						}else{
							document.getElementById('login').style.display='block';
							document.getElementById('logoutdiv').style.display = 'hide';
							hs.htmlExpand(null, {width: 400,height:100, contentId: 'facebookLoginDiv',wrapperClassName :'draggable-header'} );
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
				var genericContent="<a href='"+profileServlet+"'><img border=\"0\" src='"+response.picture.data.url+"' border='0'/></a><a href='"+profileServlet+"' class='profile'>"+response.name+"</a>";
				userInfo.innerHTML=genericContent;
				userInfo.style.visibility='visible';
				facebookId=response.id;
				loggedIn=true;
				});
		}

		function logout() {
			serverLogout();
			facebookId=null;
		}
		
		function fblogout() {
		    FB.logout(function(response) {
		    	serverLogout();
		    });
		}
		hs.graphicsDir = '<%=response.encodeURL(request.getContextPath())%>/img/highslide/graphics/';
		hs.align = 'center';
		hs.transitions = ['expand', 'crossfade'];
		hs.wrapperClassName = 'dark borderless floating-caption';
		hs.fadeInOut = true;
		hs.dimmingOpacity = .75;
		hs.showCredits = false;
		hs.allowMultipleInstances = false;

		// Add the controlbar
		if (hs.addSlideshow) hs.addSlideshow({
			slideshowGroup: 'first-group',
			interval: 5000,
			repeat: false,
			useControls: true,
			fixedControls: 'fit',
			overlayOptions: {
				opacity: .6,
				position: 'bottom center',
				hideOnMouseOut: true
			}
		});
	</script>
	<div class="main" id="main">
		<tiles:insertAttribute name="header" />
		<div class="bodyDiv" id="bodyDiv">
			<tiles:insertAttribute name="categories" />
			<tiles:insertAttribute name="body" />
			<div id="userinfo" class="userinfo" style="visibility: visible;">
			
			</div>
		</div>
	</div>
<%} %>
</body>
</html>
