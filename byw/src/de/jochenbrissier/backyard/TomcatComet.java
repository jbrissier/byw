package de.jochenbrissier.backyard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometEvent.EventType;

import com.google.inject.Inject;

/**
 * implementation for tomcat 6.0.x comet processor
 * 
 * 
 * 
 * @author jochen
 * 
 */
public class TomcatComet implements Event {

	CometEvent ev;
	boolean ready = false;
	boolean error = false;
	HttpServletRequest req;
	HttpServletResponse res;
	LinkedList<Message> list = new LinkedList<Message>();
	int timeout = 60000;
	MessagePattern mp;

	@Inject
	public TomcatComet(MessagePattern mp) {
		this.mp = mp;

	}

	public void addMessage(Message message) {
		list.add(message);

	}

	public void close() throws SendFailException {

		try {

			PrintWriter wr = res.getWriter();
			wr.write(mp.getMessages(list));
			wr.close();
			wr.flush();

			this.ev.close();
		} catch (Exception e) {
			this.ready = false;
			this.error = true;
			throw new SendFailException(e.toString());
		}

	}

	public ServletRequest getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletResponse getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void init() {

		// TODO: i don't know :)

	}

	public void init(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;

		init();

	}

	public boolean isError() {
		// TODO Auto-generated method stub
		return error;
	}

	public boolean isReady() {
		// TODO Auto-generated method stub
		return ready;
	}

	public void setEvent(Object Event) {

		if (Event instanceof CometEvent) {
			this.ev = (CometEvent) Event;
			this.res = ev.getHttpServletResponse();
			this.req = ev.getHttpServletRequest();

			try {
				ev.setTimeout(timeout);
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {

				error = true;
				ready = false;

				e.printStackTrace();
			} catch (ServletException e) {
				error = true;
				ready = false;
				e.printStackTrace();
			}
			EventType eventType = this.ev.getEventType();

			if (eventType == CometEvent.EventType.BEGIN) {
				this.ready = true;

			}
			if (eventType == CometEvent.EventType.END) {
				this.ready = false;
			}

			if (eventType == CometEvent.EventType.READ) {
				// TODO: NO IDEA! ;)
			}

			if (eventType == CometEvent.EventType.ERROR) {

				if (this.ev.getEventSubType() == CometEvent.EventSubType.TIMEOUT) {
					timeout();
				}
				if (this.ev.getEventSubType() != CometEvent.EventSubType.TIMEOUT)
					error = true;
				ready = false;
				try {
					close();
				} catch (SendFailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	public void setEventListener(EventListener eventListener) {
		// TODO Auto-generated method stub

	}

	public void setRequest(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	public void setRespons(HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	public void setTimeOut(long timeout) {
		// TODO Auto-generated method stub

	}

	private void timeout() {

		addMessage(new Message("timeout"));

	}

}
