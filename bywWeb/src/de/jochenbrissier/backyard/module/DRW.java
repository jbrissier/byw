package de.jochenbrissier.backyard.module;

import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;

import com.google.inject.Inject;

import de.jochenbrissier.backyard.core.EventListener;
import de.jochenbrissier.backyard.core.Message;
import de.jochenbrissier.backyard.util.MessagePattern;
import de.jochenbrissier.backyard.util.SendFailException;

/**
 * the start of an DRW PLUGIN for Backyard
 * 
 * @author jochen
 * 
 */
public class DRW extends BackyardEvent {

	static LinkedList<Message> list = new LinkedList<Message>();
	MessagePattern messagepattern;
	String page;

	@Inject
	public DRW(MessagePattern mp) {
		this.messagepattern = mp;
	}
	
	
	
	public DRW() {
		// TODO Auto-generated constructor stub
	}
	

	public void addMessage(Message message) {
		list.add(message);

	}

	public void close() throws SendFailException {

		if (page == null) {

			for (Message me : list)
				ScriptSessions.addFunctionCall("jQuery.backyard.newDRWMessage",
						me);

			list.clear();

		} else {
			Browser.withPage(page, new Runnable() {

				@Override
				public void run() {
					for (Message me : list)
						ScriptSessions.addFunctionCall(
								"jQuery.backyard,newDRWMessage", me);
				}
			});
		}

	}

	public void simpleAdd(String mes){
		System.out.println(mes);
		
		Message message = new Message(mes);
		
		
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
		// TODO Auto-generated method stub

	}

	public void init(HttpServletRequest req, HttpServletResponse res) {

		page = req.getRequestURI();

	}

	public boolean isError() {
		// TODO Auto-generated method stub
		return true;
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
