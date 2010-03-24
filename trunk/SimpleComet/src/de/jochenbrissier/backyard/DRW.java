package de.jochenbrissier.backyard;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DRW implements Event {

	public void addMessage(Message message) {
		// TODO Auto-generated method stub

	}

	public void close() throws SendFailException {
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
		// TODO Auto-generated method stub

	}

	public void init(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub

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
