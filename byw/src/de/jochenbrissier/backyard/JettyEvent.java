package de.jochenbrissier.backyard;

import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

import com.google.inject.Inject;

public class JettyEvent extends BackyardEvent {
	LinkedList<Message> list = new LinkedList<Message>();
	MessagePattern massagepattern;
	HttpServletRequest req;
	HttpServletResponse res;
	private long timeout = 60000;
	Continuation con;
	private boolean ready = false;
	private boolean error = false;
	
	
	@Inject
	public JettyEvent(MessagePattern mp) {
		this.massagepattern = mp;

	}

	@Override
	public void addMessage(Message message) {
		synchronized (list) {

			list.add(message);

		}

	}

	@Override
	public void close() throws SendFailException {
		super.onClose();
		//this.ready = false;
		try {
			
			
			if(con == null || res==null)
				return;
			
			PrintWriter wr = res.getWriter();
			
			wr.print(massagepattern.getMessages(list));
			wr.flush();
			wr.close();
			con.complete();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new SendFailException();

		}

	}

	@Override
	public ServletRequest getRequest() {
		// TODO Auto-generated method stub
		return req;
	}

	@Override
	public ServletResponse getResponse() {
		// TODO Auto-generated method stub
		return res;
	}

	@Override
	public long getTimeOut() {
		// TODO Auto-generated method stub
		return timeout;
	}

	@Override
 	public void init() {

		con = ContinuationSupport.getContinuation(req);
		this.ready = true;
		con.setTimeout(timeout);
		con.suspend();

	}

	@Override
	public void init(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
		init();
	}

	@Override
	public boolean isError() {
		// TODO Auto-generated method stub
		return this.error;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return this.ready;
	}

	@Override
	public void setEvent(Object Event) {
		if (Event instanceof Continuation)
			this.con = (Continuation) Event;
	}

	@Override
	public void setEventListener(EventListener eventListener) {
		// TODO: DON'T know how?!?! yet ;)
	}

	@Override
	public void setRequest(HttpServletRequest req) {

	}

	@Override
	public void setRespons(HttpServletResponse res) {

	}

	@Override
	public void setTimeOut(long timeout) {
		this.timeout = timeout;
	}
}
