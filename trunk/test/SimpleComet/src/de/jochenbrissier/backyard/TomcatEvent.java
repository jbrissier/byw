//package de.jochenbrissier.backyard;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.catalina.CometEvent;
//import org.apache.catalina.CometProcessor;
//import org.apache.catalina.HttpRequest;
//import org.apache.catalina.connector.Request;
//import  org.apache.catalina.connector.RequestFacade;
//import  org.apache.catalina.connector.Response;
//import org.apache.catalina.connector.CometEventImpl;
//import org.apache.catalina.connector.ResponseFacade;
//
//public class TomcatEvent extends HttpServlet implements Event ,CometProcessor
//{
//
//	
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//System.out.println("doPOST");
//	
//	
//
////	CometEventImpl ce = new CometEventImpl(re.getUnwrappedCoyoteRequest(),rs.getUnwrappedCoyoteResponse());
//		
////		this.event(ce);
//	
//	}
//	
//	
//	
//	public void Close() throws SendFailException {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void addMessage(Message message) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public ServletRequest getRequest() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public ServletResponse getResponse() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public long getTimeOut() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public void init() {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void init(HttpServletRequest req, HttpServletResponse res) {
//
//		try {
////			this.service(req, res);
//			this.doPost(req, res);
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//	public boolean isError() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean isReady() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public void setEvent(Object Event) {
//		
//		HttpServlet ser = (HttpServlet) Event;
//		
//				
//
//	}
//
//	public void setEventListener(EventListener eventListener) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void setRequest(HttpServletRequest req) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void setRespons(HttpServletResponse res) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void setTimeOut(long timeout) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void event(CometEvent arg0) throws IOException, ServletException {
//		System.out.println("NEW EVENT :)");
//		
//	}
//
//}
