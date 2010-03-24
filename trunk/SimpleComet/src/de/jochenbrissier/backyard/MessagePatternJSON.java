package de.jochenbrissier.backyard;

import java.util.Collection;

import com.google.inject.Inject;

public class MessagePatternJSON implements MessagePattern {

	@Inject
	public MessagePatternJSON() {
	}

	private static String getMessage(Message message) {

		// ch = channelid

		// id = UUID message UID

		// data = data of the message

		String data =  message.getData();
		
		

		//if the data not json 
		if(!data.matches("\\{.*")){
			data="\""+data+"\"";
		}
		
		String pattern = "{\"id\":\"" + message.getuID() + "\", \"ch\":\""
				+ message.getChannelName() + "\",\"data\":" + data
				+ "},";

		return pattern;

	}

	public String getMessages(Collection<Message> messages) {

		String res = "{\"messages\":[";

		for (Message me : messages) {

			res += getMessage(me);

		}

		if (messages.size() != 0 || messages.size() == 1) {
			res = res.substring(0, res.length() - 1);
		}

		res += "]}";

		return res;
	}

}
