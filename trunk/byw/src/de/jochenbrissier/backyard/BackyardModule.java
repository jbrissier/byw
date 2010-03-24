package de.jochenbrissier.backyard;

import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
 
/**
 * 
 * this is the standard bining of the interfaces
 * <ul>
 * <li>channel</li>
 * <li>Member<li>
 * <li>Event<li>
 * </ul>
 * 
 * 
 * @author jochenbrissier
 *
 */


public class BackyardModule extends AbstractModule {

	Log log = LogFactory.getLog(BackyardModule.class);

	protected String serverInfo;

	@Override
	protected void configure() {

		// bind channel
		bind(Channel.class).to(ChannelImpl.class);

		// TODO: choose the correct for the particularly servlet container
		// bind(Event.class).to(AsyncronEvent.class);

		bind(ChannelHandler.class).asEagerSingleton();

		bind(SendFailException.class);

		bind(MessagePattern.class).to(MessagePatternJSON.class);

		bind(Member.class).to(MemberImpl.class);

		bind(MemberHandler.class);

		bind(Event.class).to(SimpleThreadEvent.class);

	}

}
