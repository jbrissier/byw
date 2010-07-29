package de.jochenbrissier.backyard.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
/** bind the message pattern to MessagePaternJSON
 * 
 * so the messages to the client will be delivered as json strings
 * 
 * @author jochen
 *
 */
public class JsonModule extends AbstractModule {

	Log log = LogFactory.getLog(JsonModule.class);

	protected String serverInfo;


	protected void configure() {

		bind(MessagePattern.class).to(MessagePatternJSON.class);

	}

}
