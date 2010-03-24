package de.jochenbrissier.backyard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

/**
 * Comet implementation of the Servlet 3.0 API
 * This will currently only used by Glassfish v3
 * @author jochen brissier
 * 
 */
public class AsyncronEvent extends BackyardEvent {

	private AsyncContext ac;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private EventListener eventListener;
	private boolean availabel = false;

	LinkedList<Message> list = new LinkedList<Message>();
	MessagePattern massagepattern;

	@Inject
	public AsyncronEvent(MessagePattern mp) {

		this.massagepattern = mp;

	}

	public void close() throws SendFailException {
		super.onClose();

		try {
			PrintWriter wr = ac.getResponse().getWriter();
			wr.print(massagepattern.getMessages(list));
			wr.flush();
			wr.close();

			ac.complete();

		} catch (Exception e) {
			e.printStackTrace();
			throw new SendFailException();

		}

	}

	public void addMessage(Message message) {

		list.add(message);

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
		super.onStart();
		req.startAsync(req, res);
		this.ac = req.getAsyncContext();
		// this.ac.setTimeout(1000);
		this.ac.addListener(new AsyncListener() {

			public void onTimeout(AsyncEvent event) throws IOException {
				timeout();

			}

			public void onStartAsync(AsyncEvent event) throws IOException {
				if (eventListener != null)
					eventListener.onStart();
				event.getAsyncContext().complete();
			}

			public void onError(AsyncEvent event) throws IOException {
				if (eventListener != null)
					eventListener.onError();
				availabel = false;
				error();
			}

			public void onComplete(AsyncEvent event) throws IOException {
				if (eventListener != null)
					eventListener.onComplete();

			}
		});

		this.availabel = true;

	}

	public boolean isError() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReady() {
		// TODO Auto-generated method stub
		return availabel;
	}

	public void setEvent(Object Event) {

		this.ac = (AsyncContext) Event;

	}

	public void setEventListener(EventListener eventListener) {

		this.eventListener = eventListener;

	}

	public void setTimeOut(long timeout) {
		ac.setTimeout(timeout);

	}

	public void setRequest(HttpServletRequest req) {
		this.req = req;
	}

	public void setRespons(HttpServletResponse res) {

		this.res = res;

	}

	public void init(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
		this.init();
		availabel = true;

	}

	private void error() {
		super.onError();
	}

	private void timeout() {
		super.onTimeout();
		addMessage(new Message("Timeout"));

		try {

			close();
		} catch (SendFailException e) {
			// TODO: Error Handling
		}
		// event.getAsyncContext().complete();
		if (eventListener != null)
			eventListener.onTimeout();
	}

}
