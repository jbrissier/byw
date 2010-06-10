package de.jochenbrissier.backyard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;

/**
 * implementation for an servlet container without comet support
 * 
 * 
 * WARNING NOT WORKING YET;
 * 
 * 
 * @author jochen
 * 
 */
public class SimpleThreadEvent extends BackyardEvent {

	private boolean running = true;
	HttpServletRequest req;
	HttpServletResponse res;
	MessagePattern mp;
	boolean availabel = false;

	LinkedList<Message> messages = new LinkedList<Message>();

	@Inject
	public SimpleThreadEvent(MessagePattern mp) {

		this.mp = mp;
	}

	public void addMessage(Message message) {

		messages.add(message);
	}

	public void close() throws SendFailException {
		this.running = false;
		System.out.println("close");
		try {
			PrintWriter wr = this.res.getWriter();
			wr.print(mp.getMessages(messages));
			wr.flush();
			wr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		this.availabel = true;
		while (this.running) {
			System.out.println(this.running);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void init(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
		init();

	}

	public boolean isError() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReady() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setEvent(Object Event) {
		// TODO Auto-generated method stub

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

}
