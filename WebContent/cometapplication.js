
	
	var comet = new Backyard();
	var servlet ='/SimpleComet/ccomet';
	comet.start();
	comet.listen("test",function(mes){
		
		jQuery('#chatbox').append("<p>"+mes+"</test>");
		
		});

	
	jQuery(document).ready(function(){
	jQuery('#sendbtn').click(function(){
		for(var i=0; i<60;i++){
		comet.send("test",jQuery('#send').val());
		jQuery('#send').val("");
		jQuery("#chatbox").scrollTo('+=100px',100);	
		}
	});
	
	
	
	
jQuery('#send').bind('keypress', function(e) {

			var code = (e.keyCode ? e.keyCode : e.which);
			if (code == 13) { // Enter keycode
						comet.send("test",jQuery('#send').val());
				jQuery('#send').val("");
				jQuery("#chatbox").scrollTo('+=100px',100);	
				}

			});
	
	
	
	
	
	});

	
