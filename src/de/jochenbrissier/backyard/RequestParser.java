package de.jochenbrissier.backyard;

import java.text.ParseException;

import org.json.JSONObject;

public class RequestParser {

	public static Message parseObject(String json) {
		Message dto = new Message();

		try {
			JSONObject joob = new JSONObject(json);

			// get channel id
			dto.setChannelid(new Long(joob.getJSONObject("channel").get("id")
					.toString()));
			
			
			dto.setChannelName(joob.getJSONObject("channel").getString("name"));
			

			// get member id
			//dto.setMemberid(joob.getJSONObject("member").getInt("id"));

			// get message
			dto.setData(joob.getJSONObject("message").getString("data"));

			
			//get method
			try{
				dto.setMethode(joob.getString("method"));

			}
			catch (Exception e) {
				//TODO: LOGGING
			
			}
			
			
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dto;
	}

}
