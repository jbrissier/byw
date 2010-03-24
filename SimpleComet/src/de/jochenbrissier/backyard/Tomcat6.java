package de.jochenbrissier.backyard;

import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
/**
 * module for tomacat 6.0.x
 * @author jochen
 *
 */
public class Tomcat6 extends AbstractModule {

	Log log = LogFactory.getLog(Tomcat6.class);

	
	
	
	
	protected String serverInfo;

	@Override
	protected void configure() {

		
		bind(Event.class).to(TomcatComet.class);

	}

	

}
