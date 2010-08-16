package de.jochenbrissier.backyard.module;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;

import com.google.inject.Inject;

import de.jochenbrissier.backyard.core.EventListener;
import de.jochenbrissier.backyard.core.Message;
import de.jochenbrissier.backyard.util.MessagePattern;
import de.jochenbrissier.backyard.util.SendFailException;

public class WebsocketEvent extends BackyardEvent implements WebSocket {

	Outbound _outbound;
	boolean error = false;
	boolean ready = false;
	MessagePattern mp;

	@Inject
	public WebsocketEvent(MessagePattern mp) {
		this.mp = mp;
	}

	@Override
	public void addMessage(Message message) {
		if (_outbound != null) {
			try {
				_outbound.sendMessage(mp.getMessage(message));
			} catch (IOException e) {
				this.error = true;
			}
		}
	}

	@Override
	public void close() throws SendFailException {
		//_outbound.disconnect();
	}

	@Override
	public ServletRequest getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletResponse getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isError() {
		// TODO Auto-generated method stub
		return error;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return ready;
	}

	@Override
	public void setEvent(Object Event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEventListener(EventListener eventListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRequest(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRespons(HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTimeOut(long timeout) {
		
	}

	@Override
	public void onConnect(Outbound out) {

		_outbound = out;
		this.ready = true;
		this.error = false;

	}

	@Override
	public void onDisconnect() {
		this.ready = false;

	}

	@Override
	public void onMessage(byte arg0, String arg1) {

		// TODO: get messages over socket and redirect them over backyard engine
	}

	@Override
	public void onMessage(byte arg0, byte[] arg1, int arg2, int arg3) {
		// TODO: get messages over socket and redirect them over backyard engine

	}

}
