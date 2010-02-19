package de.jochenbrissier.backyard;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SimpleThreadEvent implements Event {

	HttpServlet servlet;
	
	
	
	
	public void close() throws SendFailException {
	servlet.notify();
	System.out.println("close");
	
	}

	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		
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
//		try {
//			servlet.wait(1000);
//		System.out.println("wait");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
	}

	public void init(HttpServletRequest req, HttpServletResponse res) {
		
		
		HttpSession session = req.getSession(true);
		try {
		synchronized(session ){
		session.wait(60000);
		}
		} catch (InterruptedException ex) {
		ex.printStackTrace();
		}
		
		
	}

	public boolean isError() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEvent(Object Event) {
	
		if(Event instanceof HttpServlet)
			servlet=(HttpServlet)Event;
		
		
		
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
