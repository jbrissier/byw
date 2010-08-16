package de.jochenbrissier.backyard.web;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import de.jochenbrissier.backyard.core.Backyard;
import de.jochenbrissier.backyard.core.Member;
import de.jochenbrissier.backyard.core.WebSocketMember;
import de.jochenbrissier.backyard.core.WebsocketModule;
import de.jochenbrissier.backyard.module.WebsocketEvent;
import de.jochenbrissier.backyard.util.MessagePatternJSON;

public class BackyardSocket extends WebSocketServlet {

	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest req, String arg1) {
	
		System.out.println("Websocket--------------------<<<--@");
		
		//get socket event 

		WebsocketEvent ev= (WebsocketEvent) Backyard.getSocketMember(req).getEvent();
		
		return ev;
	}

}
