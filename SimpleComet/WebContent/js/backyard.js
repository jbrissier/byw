//globals
var channels = new Array();
var debug = debug;
var ack = 'ACK';
var error = 'ERROR';
var end = 'END';
var debug = false;
var servlet= "";
var userid="";


// sample message object
var json = {
	channel : {
		name : 'meta',
		id : 0
	},
	message : {
		data : ''
	}
}

var Backyard = Class
		.create( {

			initalize : function() {

			},

			send : function(Channel, data) {

				return this.publish("message={'channel':{'name':'" + Channel+ "','id':0},message:{'data':'" + data+ "'},method:'send'}");

			},

			start : function() {

				return this
						.publish("message={'channel':{'name':'meta','id':0},message:{'data':'no'}}");
			},

			publish : function(data) {
				// connect to meta channel

			if (this.debug)
				cosole.log("Start Comet")

			new Ajax.Request(
					servlet,
					{

						method : "POST",
						parameters : data,
						onSuccess : function(mes) {

							var res = mes.responseText;

							// if no replay from server
							if (res == "")
								return 

							

														

							

														

							

							if (this.debug)
								console.log("Server sends: " + res);

							// if handshake
							if (res == 'ACK')
								return new Backyard()
										.publish("message={'channel':{'name':'meta','id':0},message:{'data':'no'}}");

							// if timeout or error
							if (res == 'ERROR'){
								
								return new Backyard()	
								.publish("message={'channel':{'name':'meta','id':0},message:{'data':'no'}}");
							}
							// parsing json object
							var jsonrs = res.evalJSON();

							// do somthing with the json data

							var arr = channels;

							for ( var i = 0; i < arr.length; i++) {

								var item = arr[i];

								if (item.name == jsonrs.channel.name) {

									item.invoke(jsonrs.message.data);

								}

							}

							return new Backyard()
									.publish("message={'channel':{'name':'meta','id':0},message:{'data':'no'}}");

						}

					});// ajax

		},

		listen : function(channel, invoke) {

			// add channel to list

			var jsonobj = {
				channel : {
					name : channel,
					id : -1
				},
				message : {
					data : ''
				}
			};

			var jsonstring = Object.toJSON(jsonobj);

			new Ajax.Request(servlet, {

				method : "POST",
				parameters : "message=" + jsonstring,
				onSuccess : function(mes) {

				}

			});

			channels.push( {
				name : channel,
				invoke : invoke
			});

		}

		});
