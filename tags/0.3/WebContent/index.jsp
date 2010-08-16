<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.backyard.js"></script>





<script type="text/javascript">




jQuery(document).ready(function(){
jQuery.backyard({url:'ccomet',failover:false});

jQuery.backyard.listen('fu',function(mes){

jQuery('#messages').append(mes+"<br/>");
	
});


jQuery('#btn').click(function(){

jQuery.backyard.publish('fu',jQuery('#mes').val());

	
});




});

</script>

<link rel="stylesheet" type="text/css" href="style.css">
<title>Insert title here</title>





</head>
<body>
<h1>byw Test</h1>
<input type="text" id="mes" value="test"></input><input type="submit" id="btn" value="test">

<h3>Messages</h3>
<div id="messages"></div>


</body>
</html>