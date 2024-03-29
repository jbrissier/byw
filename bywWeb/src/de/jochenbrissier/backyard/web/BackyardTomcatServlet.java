package de.jochenbrissier.backyard.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import de.jochenbrissier.backyard.core.Backyard;
import de.jochenbrissier.backyard.util.ServerDedec;

/**
 * This servlet is the communications backend to the jquery plugin <br>
 *set this servelt in your web.xml to use the plugin. <br>
 *This supports Tomcat 6.0.x
 * 
 * 
 * 
 * 
 * @author jochen brissier
 * 
 */

public class BackyardTomcatServlet extends HttpServlet implements
		CometProcessor {

	Log log = LogFactory.getLog(BackyardTomcatServlet.class);

	@Override
	public void init() throws ServletException {
		// Auto detect the server
		Backyard.autoDedectImpl(this);

		// register the channelhandler
		Backyard.registCannelHandler(this);
	}

	private void send(HttpServletRequest req, HttpServletResponse resp,
			JSONObject json) {

	}

	private HttpServletRequest req;
	private HttpServletResponse res;

	@Override
	public void service(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {

		if (ServerDedec.isServer(ServerDedec.TOMCAT6, this)) {
			log
					.warn("TOMCAT6 invoked service method... config your server.xml and change adapter");
			// super.service(req, res);
			return;

		}

		req.setAttribute("de.jochenbrissier.byw.comet", "noneTomcat");

		event(new CometEvent() {

			@Override
			public void setTimeout(int arg0) throws IOException,
					ServletException, UnsupportedOperationException {
				// TODO Auto-generated method stub

			}

			@Override
			public HttpServletResponse getHttpServletResponse() {
				// TODO Auto-generated method stub
				return res;
			}

			@Override
			public HttpServletRequest getHttpServletRequest() {
				// TODO Auto-generated method stub
				return req;
			}

			@Override
			public EventType getEventType() {
				// TODO Auto-generated method stub
				return EventType.BEGIN;
			}

			@Override
			public EventSubType getEventSubType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void close() throws IOException {
				// TODO Auto-generated method stub

			}
		});

	}

	private void listenChannel(HttpServletRequest req,
			HttpServletResponse resp, JSONObject json, Backyard backyard) {

		String channelName = json.getJSONObject("channel").getString(
				"channel_name");
		backyard.listenToChannel(channelName);

	}

	public void event(CometEvent ev) throws IOException, ServletException {

		log.debug("enter service backyard servlet");

		log.debug("enter BackyardServlet");

		HttpServletRequest req = ev.getHttpServletRequest();
		HttpServletResponse resp = ev.getHttpServletResponse();

		//tomcat character encoding fix ... 
		resp.setCharacterEncoding(req.getCharacterEncoding());
		
		
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

				PrintWriter pw = ev.getHttpServletResponse().getWriter();

				resp.getWriter().print(
						"{\"status\":\"OK\",\"id\":\""
								+ req.getSession().getId()
								+ "\",\"websocket\":\""
								+ Backyard.isWebsocketSupport() + "\"}");
				pw.flush();
				pw.close();

				return;

			}

			if (function.matches("comet")) {
				log.debug("comet");

				// if the implementation is a non Tomcat implementation invoke
				// the normal
				if (req.getAttribute("de.jochenbrissier.byw.comet") != null
						&& req.getAttribute("de.jochenbrissier.byw.comet")
								.equals("nonTomcat")) {
					backyard.startAsync();
				} else {
					backyard.startAsync(ev);
				}
			}

			if (function.matches("listen")) {
				log.debug("listen");
				listenChannel(req, resp, json, backyard);
				ev.close();
			}

			if (function.matches("send")) {

				log.debug("sendtochannel");

				JSONObject channeldata = json.getJSONObject("channel");

				try {

					backyard.getChannel(channeldata.getString("channel_name"))
							.sendMessage(json.getString("data"));

				} catch (Exception e) {
					e.printStackTrace();
				}
				ev.close();
			}
		} catch (Exception e) {

			log.warn(e);
		}
	}

}
