var loggedIn=false;
var facebookId='';
var popupMode=false;
function clearInput(control,defvalue){
	debugger;
	if(control.value!=null && control.value!='undefined' && control.value==defvalue){
		control.value="";
	}
}

function clearDojoInput(event){
	var control=event.srcElement;
	if(control.value!=null && control.value!='undefined' && control.value==control.defValue){
		control.value="";
	}
}

function restoreInputValue(control,defvalue){
	if(control.value==''){
		control.value=defvalue;
	}
}

function restoreDojoInputValue(event){
	var control=event.srcElement;
	if(control.value==''){
		control.value=control.defValue;
	}
}

function showPopup(popupId){
	var selectedPopup=document.getElementById(popupId);
	selectedPopup.style.visibility='visible';
}

function hidePopup(popupId){
	var selectedPopup=document.getElementById(popupId);
	selectedPopup.style.visibility='hidden';
}


function followPoi(component){
	$.ajax({
		  url: servletUrl+"/in/follow?poiId="+selectedPoiId,
		  success:function (data) {
			component.style.display='none';		
		  }
	}); 
}
function followTalk(component,talkId){
	debugger;
	alert("talk id:"+talkId+" url:"+servletUrl+"/talk/follow?talkId="+talkId);
	$.ajax({
			   url: servletUrl+"/talk/follow?talkId="+talkId, 
			   success: function (data) {
					   component.style.display='none';				  
			   } 
		}); 
}

function followSubscriber(component,profileId){
		$.ajax({
			   url: servletUrl+"/subs/watch?profileId="+profileId, 
			   success: function (data) {
					   component.style.display='none';				  
			   }
			 } 
			); 
}

function likeComment(commentId){
	if(checkLoggedIn()){
		dojo.io.bind ( 
			 { 
			   url: servletUrl+"/subs/tupc?commentId="+commentId, 
			   load: function (type, data, evt) {
						  alert(data);
			   },
			   mimetype: "text/html" 
			 } 
			); 
	}
}

function dislikeComment(commentId){
	if(checkLoggedIn()){
		dojo.io.bind ( 
				 { 
				   url: servletUrl+"/subs/tdownc?commentId="+commentId, 
				   load: function (type, data, evt) {
							  
				   },
				   mimetype: "text/html" 
				 } 
				); 
	}
}


function checkLoggedIn(){
	if(loggedIn==false){
		hs.htmlExpand(null, {width: 400, contentId: 'facebookLoginDiv',wrapperClassName :'draggable-header'} );
		popupMode=true;
		return false;
	}else{
		return true;
	}
}

function validateEmail(email) { 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
} 


function addToList(){
	var emailAddress=document.getElementById("emailInput").value;
	if(emailAddress!=null && emailAddress!='undefined' && validateEmail(emailAddress)==true){
		  var table = document.getElementById("mailListTable");
	      var rowCount = table.rows.length;
	      if(rowCount>4){
	    	  alert("5 mail adresinden fazlasını ekleyemezsiniz!");
	    	  return false;
	      }
	      var row = table.insertRow(rowCount);
	      row.id="emailId"+table.rows.length;
	      var addressCell = row.insertCell(0);
	      addressCell.innerHTML=emailAddress;
	      var removeLink = row.insertCell(1);
	      removeLink.innerHTML="<a href='#' onclick='removeMailFromList(\""+(row.id)+"\")' class='profile'>Kaldır</a>";
	      document.getElementById("emailInput").value="";
	}else{
		alert("Lüften geçerli bir mail adresi girin!");
	}
}

function removeMailFromList(rowIndex){
	 var row=document.getElementById(rowIndex);
	 row.parentNode.removeChild(row);
}

function validateInvitationForm(){
	var table = document.getElementById("mailListTable");
    var rowCount = table.rows.length;
    if(rowCount<1){
    	alert("En az bir arkadaşınızı davet etmelisiniz!");
    	return false;
    }else{
		var bulkMail="";
        for(var i=0; i<rowCount; i++) {
            var row = table.rows[i];
            var mail = row.cells[0].innerHTML;
            bulkMail+=mail+",";
        }
        document.getElementById("mailAddressContainer").value=bulkMail;
        document.getElementById("invitationForm").submit();
    }
}
