//package de.jochenbrissier.backyard;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.catalina.CometEvent;
//
//import com.google.inject.Guice;
//import com.google.inject.Inject;
//import com.google.inject.Injector;
//
///**
// * Servlet implementation class comet
// */
//public class TomcatBackyard extends HttpServlet implements CometProcessor {
//	private static final long serialVersionUID = 1L;
//	private static Injector in = Guice.createInjector(new BackyardModule());
//
//
//
//	public void event(CometEvent event) throws IOException, ServletException {
//	
//		 System.out.println(event.getEventType());
//		
//		
//	
//	
//	}
//}
