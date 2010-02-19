(function($) {

	// globals
	var connected = false;
	var channels = new Array();
	var servlet_url;

	$.fn.backyard = function(options) {
		var defaults = {
			url : 'BackyardServlet'

		};

		var options = $.extend(defaults, options);

		return this.each(function() {

			var obj = $(this);

			// set servlet
				servlet_url = options.url;

				handshake(options.url);

			});



	};// backyard

	
	
	
	
	function handshake(servlet_url) {

		var handshake_json = "{function:'handshake'}"

		jQuery.ajax( {
			type : 'POST',
			url : servlet_url,
			data : {
				data : handshake_json
			},
			
			error:function(){
				
				connected=false;
				
			}
			,
			success : function() {
				if (!connected) {
					channels.push("meta");
					connected = true;
				}
				
					handshake(servlet_url);

			}

		});

	}// handshake
	
	
	
	
	
	
	
	
	$.fn.backyard.listen = function(channel, callback){
		var listen_json = "{function:'listen', channel:{channel_name:'"
				+ channel + "'}}";

		

			jQuery.ajax( {
				type : 'POST',
				url : servlet_url,
				data : {
					data : listen_json
				},
				success : function(mes) {
					if (connected) {
						channels.push(channel);
						callback(mes);
					}	

				}

			});// ajax



	}

	$.fn.backyard.publish = function(channel, message) {

		var publish_json = "{function:'send',channel:{channel_name:'" + channel
				+ "'},data:" + message + "}";

		jQuery.ajax( {
			type : 'POST',
			url : servlet_url,
			data : {
				data : publish_json
			},
			success : function() {
				if (connected) {
					channels.push(channel);
				}

			}

		});// ajax

	}

	// util functions

	function hasChannel(channel_name) {

		for ( var i = 0; i < channels.length; i++) {
			if (channels[i] == channel_name) {
				return true;
			}
		}
		return false;

	}

})(jQuery);
