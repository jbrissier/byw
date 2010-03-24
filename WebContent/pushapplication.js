(function($) {
	$(document).ready(function() {
		var xhr;
		var timer;
		var rstext = "";

		$('#send').bind('keypress', function(e) {

			var code = (e.keyCode ? e.keyCode : e.which);
			if (code == 13) { // Enter keycode
					sendMessage();
				}

			});

		function sendMessage() {

			var mes = $('#send').val();
			$('#send').val("");

			$.ajax( {
				type : 'POST',
				url : '/SimpleComet/SendMessage',
				data : "message=" + mes,
				success:function(mes){

					if(mes=="ERROR"){
						alert(mes);
						clearTimeout(timer);
						cometConnect();
					}
					
					
				}

			});

		}

		$('#sendMessage').click(sendMessage);

		$('#sender').click(function() {

			$('#login').hide();
			$('#messager').show();
			cometConnect();

		});

		function cometConnect() {

			xhr = $.ajax( {
				type : "POST",
				url : '/SimpleComet/comet',
				data : "name=" + $('#name').val(),
				success : function(){cometConnect();},

				complete : function() {
					//$('#chatbox').append("stop!!");
					clearTimeout(timer);
				}

			});// ajax

			xhr.onreadystatechange = function() {

				if (xhr.readyState == 3) {

					var timer = setTimeout(function() {
						//raus pasen aus respons
						var lines = xhr.responseText.split("\n");
						var latest = lines[lines.length - 2];

					var newen= 	$("#chatbox").append("<p>" + latest + "</p>");
					$("#chatbox").scrollTo('+=100px',100);	
				
						
						
						
						
						
						
					}, 100);

				}
			};

		}

	});
})(jQuery);