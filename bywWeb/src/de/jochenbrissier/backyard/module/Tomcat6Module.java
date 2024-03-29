package de.jochenbrissier.backyard.module;

import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.jochenbrissier.backyard.core.Event;

/**
 * module for tomacat 6.0.x
 * 
 * @author jochen
 * 
 */
public class Tomcat6Module extends AbstractModule {

	Log log = LogFactory.getLog(Tomcat6Module.class);

	protected String serverInfo;

	@Override
	protected void configure() {

		bind(Event.class).to(TomcatComet.class);

	}

}
