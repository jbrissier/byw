package de.jochenbrissier.backyard.web;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.jochenbrissier.backyard.core.Backyard;

@Path("/publish")
public class Publish {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String publish(@QueryParam("channel") String channel,
			@QueryParam("data") String data) {
		try {
			Backyard.publish(channel, data);
		} catch (NullPointerException e) {
			return "Backyard not started";

		} catch (Exception e) {
			return e.getMessage();
		}

		return "ok";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String postpublish(@FormParam("channel") String channel,
			@FormParam("data") String data) {
		try {
			Backyard.publish(channel, data);
		} catch (NullPointerException e) {
			return "Backyard not started";

		} catch (Exception e) {
			return e.getMessage();
		}

		return "ok";
	}
	
	
	
	
	
	
	
}
