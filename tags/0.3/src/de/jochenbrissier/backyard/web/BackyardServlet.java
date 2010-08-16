package de.jochenbrissier.backyard.web;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import de.jochenbrissier.backyard.core.Backyard;

/**
 * This servlet is the communications backend to the jquery plugin <br>
 *set this servelt in your web.xml to use the plugin. <br>
 *This supports Glassfish v3 and Jetty 6
 * 
 *
 * @author jochen brissier
 * 
 */

@WebServlet(asyncSupported = true)
public class BackyardServlet extends HttpServlet {

	Log log = LogFactory.getLog(BackyardServlet.class);

	@Override
	public void init() throws ServletException {
		Backyard.autoDedectImpl(this);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.debug("enter service backyard servlet");

		log.debug("enter BackyardServlet");

		// Backyard API

		Backyard backyard = new Backyard(req, resp);

		backyard.setServlet(this);

		// json obj
		String data = req.getParameter("data");

		try {
			log.debug("Parsing JSON: " + data);
			JSONObject json = new JSONObject(data);

			// get function from request json
			String function = json.getString("fn");

			if (function.matches("handshake")) {
				log.debug("Handshake");

				// TODO: GENERATE AN ID OR SEND THE SESSION ID TO THE CLIENT

				resp.getWriter().print(
						"{\"status\":\"OK\",\"id\":\""
								+ req.getSession().getId() + "\",\"websocket\":\""+Backyard.isWebsocketSupport()+"\"}");

				return;

			}

			if (function.matches("comet")) {
				log.debug("comet");

				backyard.startAsync();

			}

			// if the json obj contains the function listen
			if (function.matches("listen")) {
				log.debug("listen");
				listenChannel(req, resp, json, backyard);
			}
			// if the json obj contains the function send

			if (function.matches("send")) {

				log.debug("sendtochannel");

				JSONObject channeldata = json.getJSONObject("channel");

				try {

					backyard.getChannel(channeldata.getString("channel_name"))
							.sendMessage(json.getString("data"));

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (ParseException e) {

			log.warn(e);
		}

	}

	private void send(HttpServletRequest req, HttpServletResponse resp,
			JSONObject json) {

	}

	private void listenChannel(HttpServletRequest req,
			HttpServletResponse resp, JSONObject json, Backyard backyard) {

		String channelName = json.getJSONObject("channel").getString(
				"channel_name");
		backyard.listenToChannel(channelName);

	}

}
