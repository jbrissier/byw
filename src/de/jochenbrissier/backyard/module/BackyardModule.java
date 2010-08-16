package de.jochenbrissier.backyard.module;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;

import de.jochenbrissier.backyard.core.Channel;
import de.jochenbrissier.backyard.core.ChannelHandler;
import de.jochenbrissier.backyard.core.ChannelImpl;
import de.jochenbrissier.backyard.core.Event;
import de.jochenbrissier.backyard.core.Member;
import de.jochenbrissier.backyard.core.MemberHandler;
import de.jochenbrissier.backyard.core.MemberImpl;
import de.jochenbrissier.backyard.util.MessagePattern;
import de.jochenbrissier.backyard.util.MessagePatternJSON;
import de.jochenbrissier.backyard.util.SendFailException;
 
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
		bind(Event.class).to(AsyncronEvent.class);

		bind(ChannelHandler.class).asEagerSingleton();

		bind(SendFailException.class);

		bind(MessagePattern.class).to(MessagePatternJSON.class);

		bind(Member.class).to(MemberImpl.class);

		bind(MemberHandler.class);

		bind(Event.class).to(SimpleThreadEvent.class);

	}

}
