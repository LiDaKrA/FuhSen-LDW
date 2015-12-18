
$(document).ready(function() {
	
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	
	$(".loading_container").hide();
	$(".ProcessDataDiv").hide();
	$(".SearchGooglePlusDiv").hide();
	$(".SearcheBayDiv").hide();
	$(".CompleteProfileDiv").hide();
	$(".loadingProfilesGIF").hide();
	
	
	$( ".CleanDS_buttom" ).click(function() {
		
		var response = confirm("Would you like to delete the current cases?");
		if (response == true) {
		    ajaxdeletecases();
		} 
		
		});
	
	
});

function ajaxdeletecases(){
	 
    $.ajax({
        url: "InterfaceAPIServlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({ FunctionFuseki: "CleanDataset"}),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
        	console.log("success");
        },
        error:function(data,status,er) {
        	console.log("error");
        }
    });
}

$(function() {
    $(".ReportedDateText").datepicker();
  });




function ShowProfilesDiv(event){
	event.preventDefault(); 
	$(".CompleteProfileDiv").hide();
	$(".SearcheBayDiv").hide();
	$(".SearchGooglePlusDiv").show();
	
}

function SearchProfiles(){
	$(".ResultsProfile").html("");
	$(".ResultsProfile").html("");
	$(".loadingProfilesGIF").show();
	var suspect= $("#SearchGoogleTextBox").val();
	
	if(suspect!=""){
		AJAXSearchProfile(suspect);	
	}
}


function SearcheBay(){
	$(".ResultseBay").html("");

	var Item= $("#SearcheBayTextBox").val();
	var LocationReported = $("#CasePlace").text();
	var DateReported = $("#CaseDate").text();
	
	if((Item!="")&&(LocationReported!="")&&(DateReported!="")){
		$(".loadingProfilesGIF").show();
		AJAXSearcheBay(Item,LocationReported,DateReported);	
	}
}

function ProcessRequest(Row){
	var Id 			=	Row.children[0].innerText;
	var Description =	Row.children[1].innerText;
	var Object 		=	Row.children[2].innerText;
	var Place 		=	Row.children[3].innerText;
	var Date 		=	Row.children[4].innerText;
	var Suspect 	=	Row.children[5].innerText;
	
	

	$("#CaseId").text(Id);
	$("#DescriptionTextArea").val(Description);
	$("#CaseDate").text(Date);
	$("#CasePlace").text("Germany");
	$("#ResearchObject").text(Object);
	$(".fuseki_div").hide();
	$(".ProcessDataDiv").show();

	AJAXListProfiles(Id);
	
}

function AJAXListProfiles(CaseId){
	
	 
    $.ajax({
        url: "InterfaceAPIServlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({ CaseId: CaseId, FunctionFuseki: "ListProfiles"}),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
        	console.log("success");
        	var ResponseJSON = JSON.parse(data);
        	$( ".profilesTable" ).append(ResponseJSON["fuseki"]);
        	
        },
        error:function(data,status,er) {
        	console.log("error");
        }
    });
}


function AJAXSearchProfile(Suspect){
	
	 
    $.ajax({
        url: "InterfaceAPIServlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({ SuspectName: Suspect, FunctionGooglePlus: "QueryName" }),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
        	console.log("success");
        	var ResponseJSON = JSON.parse(data);
        	$( ".ResultsProfile" ).append(ResponseJSON["googleplus"]);
        	$(".loadingProfilesGIF").hide();
        },
        error:function(data,status,er) {
        	console.log("error");
        }
    });
}

function AJAXSearcheBay(ResearchObject,LocationReported,DateReported){
	
    $.ajax({
        url: "InterfaceAPIServlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({ FunctioneBay: "QueryItem" , ResearchObject: ResearchObject, LocationReported : LocationReported , DateReported : DateReported}),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
        	console.log("success");
        	var ResponseJSON = JSON.parse(data);
        	$(".descriptioneBaySearch").text(ResearchObject +" offered in eBay since "+DateReported+" in Germany");
        	$( ".ResultseBay" ).html("");
        	$( ".ResultseBay" ).append(ResponseJSON["eBay"]);
        	$(".loadingProfilesGIF").hide();
        	$( ".ResultseBay" ).show();
        },
        error:function(data,status,er) {
        	console.log("error");
        }
    });
}


function  AJAXInsertProfile(CaseId,SuspectNameProfile,SuspectId){
	
	 $.ajax({
	        url: "InterfaceAPIServlet",
	        type: 'POST',
	        dataType: 'json',
	        data: JSON.stringify({ FunctionFuseki: "InsertProfile" , CaseId: CaseId , SuspectNameProfile: SuspectNameProfile , SuspectId: SuspectId}),
	        contentType: 'application/json',
	        mimeType: 'application/json',
	        success: function (data) {
	        	var ResponseJSON = JSON.parse(data);
	        	console.log("success: ");
	        	console.log(ResponseJSON["fuseki"]);
	        },
	        error:function(data,status,er) {
	        	console.log("error");
	        }
	    });
	
}


function addProfile(event,Object){
	event.preventDefault(); 
	var indexRow 	=	$('#profilesTable >tbody >tr').length +1;
	var nameSuspect =	$("#Profile-"+Object.id).text();
	var CaseId		=	$("#CaseId").text();
	var icon =	"<input id="+Object.id+" onClick='ShowCompleteProfile(event,this)' class='searchImage' type='image' src='Resources/Images/profile.png'>";
	$('#profilesTable').append('<tr><td>'+indexRow+'</td><td>'+nameSuspect+'</td><td>'+icon+'</td></tr>');
	
	AJAXInsertProfile(CaseId,nameSuspect,Object.id);
}

function sendAjaxRequest(Suspect,ResearchObject) {
	 
 
    $.ajax({
        url: "InterfaceAPIServlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({ SuspectName: Suspect,ResearchObject:ResearchObject}),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
        	console.log("success");
        	var ResponseJSON = JSON.parse(data);
        	$( ".GoogleAPI_container" ).append(ResponseJSON["googleplus"]);
        	$( ".eBayAPI_container" ).append(ResponseJSON["eBay"]);
        	$(".loading_container").hide();
        	$(".fuseki_div").show();
        },
        error:function(data,status,er) {
        	console.log("error");
        }
    });
}

function ShowCompleteProfile(event,Object){
	$(".SearchGooglePlusDiv").hide();
	$(".SearcheBayDiv").hide();
	event.preventDefault(); 
	$("#ProfilePicture").attr("src","");
	$(".ProfilePersonalInfoDiv").html("");
	$(".ProfileDownDivPost").html("");
	ChangeTABSocialInfo("socialInfoTableTD1");
	cleanProfile();
	AJAXgetProfile(Object.id);

	
}

function ShowEbayDiv(event){
	
	event.preventDefault(); 
	$(".CompleteProfileDiv").hide();
	$(".SearchGooglePlusDiv").hide();
	$(".SearcheBayDiv").show();
	$( ".ResultseBay" ).hide();
	var Item =	$("#ResearchObject").text();
	var LocationReported = $("#CasePlace").text();
	var DateReported = $("#CaseDate").text();
	
	if((Item!="")&&(LocationReported!="")&&(DateReported!="")){
		$(".loadingProfilesGIF").show();
		AJAXSearcheBay(Item,LocationReported,DateReported);	
	}
	
}
function ChangeTABSocialInfo(ID_TAB){
	var TD= "socialInfoTableTD";
	var DIV= "ProfileDownDivMenu";
	for(var i=1;i<=6;i++){
		$("."+DIV+i).hide();
		$("#"+TD+i).css("color","black");
	}
	$("."+DIV+ID_TAB.replace(TD,"")).show();
	$("#"+ID_TAB).css("color","#F1784B");
}
function cleanProfile(){
	var DIV= "ProfileDownDivMenu";

	for(var i=1;i<=6;i++){
		$("."+DIV+i).html("");
	}
	$(".ProfileLinksDiv").html("");
}



function  AJAXgetProfile(ProfileId){
	
	 $.ajax({
	        url: "InterfaceAPIServlet",
	        type: 'POST',
	        dataType: 'json',
	        data: JSON.stringify({ ProfileId: ProfileId, FunctionGooglePlus: "GetProfile" }),
	        contentType: 'application/json',
	        mimeType: 'application/json',
	        success: function (data) {
	        	console.log("success: ");
	        	var ResponseJSON = JSON.parse(data);
	        	$("#ProfilePicture").attr("src",ResponseJSON["image_src"]);
	        	$(".ProfilePersonalInfoDiv").append(ResponseJSON["InfoProfile"]);
	        	$(".ProfileDownDivMenu1").append(ResponseJSON["posts"]);
	        	$(".ProfileDownDivMenu2").append(ResponseJSON["Study"]);
	        	$(".ProfileDownDivMenu3").append(ResponseJSON["Work"]);
	        	$(".ProfileLinksDiv").append(ResponseJSON["URLS"]);
	        	$(".CompleteProfileDiv").show();
	        	
	        },
	        error:function(data,status,er) {
	        	console.log("error");
	        }
	    });
	
}
