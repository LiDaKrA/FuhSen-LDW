<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
$(document).ready(function(){
	
	var url =window.location.href;
	var seekseed = "code";
	var code = url.substring(url.indexOf(seekseed)+seekseed.length+1,url.indexOf("&"));

	
	seekseed = "&state=";
	var query = url.substring(url.indexOf(seekseed)+seekseed.length,url.indexOf("%25"));
	
	seekseed = "%25type%3D";
	var type = url.substring(url.indexOf(seekseed)+seekseed.length,url.indexOf("#"));
	
	
	var redirectUri ="http://linkeddatawrapper-dataextraction.rhcloud.com/ldw/facebook/search.html";
	//var redirectUri ="http://localhost:8080/DataWrapper/facebook/search.html";
	window.location.replace(redirectUri+"?code="+code+"&query="+query+"&type="+type);
});
</script>
</head>
<body>

</body>
</html>


