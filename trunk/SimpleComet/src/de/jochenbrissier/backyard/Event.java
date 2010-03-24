package de.jochenbrissier.backyard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;


/**
 * Event is the representation of a comet procession cycle
 * @author jochen
 *
 */

public interface Event {

	public void setEventListener(EventListener eventListener);

	public void setEvent(Object Event);

	public void setTimeOut(long timeout);
	
	public long getTimeOut();
	
	public	boolean isError();
	
	
	public void close() throws SendFailException;

	public ServletRequest getRequest();

	public	ServletResponse getResponse();

	public void setRequest(HttpServletRequest req);

	public	void setRespons(HttpServletResponse res);
	
	
	public boolean isReady();
	
	public void addMessage(Message message);
	
	public void init();
	
	public void init(HttpServletRequest req, HttpServletResponse res);
	

}
