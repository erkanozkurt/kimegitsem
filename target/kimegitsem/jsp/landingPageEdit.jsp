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
	width: 500px;
	position: absolute;
	top: 50%;
	left: 35%;	
	background-color: white;
	filter:alpha(opacity=80); 
   -moz-opacity: 0.8; 
   opacity: 0.8; 
}
</style>
<script type="text/javascript" src="../js/ckeditor.js"></script>
<script src="../js/sample.js" type="text/javascript"></script>
<link href="../css/sample.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var editor;

//The instanceReady event is fired, when an instance of CKEditor has finished
//its initialization.
CKEDITOR.on( 'instanceReady', function( ev )
	{
		editor = ev.editor;

		// Show this "on" button.
	});
function validate() {
	var oEditorHTML = CKEDITOR.instances.editor1.getData();
	document.getElementById("text").value=oEditorHTML;
}
</script>
</head>
<body>
	<div id="comment">	
		<br/>
		<s:form action="%{context}?saveEdit" method="post" onsubmit="return validate()"  enctype="multipart/form-data">
			<s:hidden id="text" value="%{text}" name="text"></s:hidden>
			
			<s:file name="poiImage" label="%{getText('label.image')}"> </s:file>
			<textarea class="ckeditor" id="editor1" name="editor1" cols="100" rows="10"><s:text name="%{text}"/></textarea>
			<s:textfield name="username" label="%{getText('label.username')}"
					size="20" maxlength="100" id="username" />
				<s:password name="password" label="%{getText('label.password')}"
					size="20" maxlength="100" id="password" />
			<s:submit value="%{getText('button.label.submit')}"/>			
		</s:form>
	</div>

</body>
</html>