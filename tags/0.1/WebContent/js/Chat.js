var index = 0;
var name = "kein name";

// events
jQuery(document).ready(function() {

	// start dwr reverse ajax
		jQuery.backyard();

		jQuery.backyard.listen("Chat", newMessage);

		jQuery('#chat_box').css("height", window.innerHeight - 100);

		jQuery('#chat_input').bind('keypress', function(e) {

			var input = jQuery('#chat_input');

			var code = (e.keyCode ? e.keyCode : e.which);
			if (code == 13) { // Enter keycode
					var text = input.val();

					sendMessage(name, text);

					input.val("");

				}

			});

		jQuery('#name_box').bind("blur", function() {

			name = jQuery('#name_box').val();

		});

	});

function sendMessage(na, me) {

	var messageobj = {
		name : na,
		message : me
	};

	jQuery.backyard.publish("Chat", messageobj);

}

function newMessage(mes) {

	index++;

	jQuery('#chat_box').append(
			"<p><span class='name'>" + mes.name + "</span>" + ": "
					+ mes.message + "</p>");

	var messages = jQuery('#chat_box > p');

	// die letzen nachrichten werden entfernt (View)
	if (messages.size() > 10) {

		messages.eq(0).remove();
	}

	// nach erfolgreicher Antwort des Servers wird die Funktion
	// rekursiv erneut aufgerufen.

}
