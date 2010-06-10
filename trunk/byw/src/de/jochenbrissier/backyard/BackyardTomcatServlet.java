package de.jochenbrissier.backyard;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.internal.oxm.schema.model.List;
import org.json.JSONObject;

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

		Backyard.autoDedectImpl(this);

	}

	private void send(HttpServletRequest req, HttpServletResponse resp,
			JSONObject json) {

	}

	
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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

				pw.print("{\"status\":\"OK\"}");
				pw.flush();
				pw.close();

				return;

			}

			if (function.matches("comet")) {
				log.debug("comet");

				backyard.startAsync(ev);

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