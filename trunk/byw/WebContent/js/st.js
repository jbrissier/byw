var fc = false;

jQuery(document).ready(function() {

	// comet
		jQuery.backyard( {
			url : "comet"
		});

		jQuery("#textbox_meta").bind("keyup", function() {

			jQuery.backyard.publish("2", jQuery("#textbox_meta").val());

		});

		jQuery("#textbox_meta").bind("focus", function() {

			fc = true;

		});

		jQuery("#textbox_meta").bind("focusout", function() {

			fc = false;

		});

		jQuery.backyard.listen("2", function(mes) {

			if (!fc)
				jQuery("#textbox_meta").val(mes);

		});

	});