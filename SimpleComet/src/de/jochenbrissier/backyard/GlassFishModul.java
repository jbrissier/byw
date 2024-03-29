package de.jochenbrissier.backyard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;

public class GlassFishModul extends AbstractModule {

	Log log = LogFactory.getLog(GlassFishModul.class);

	
	protected String serverInfo;

	@Override
	protected void configure() {


		// bind channel
		bind(Channel.class).to(ChannelImpl.class);

		// bind async implementation

		// TODO: choose the correct for the particularly servlet container
		 bind(Event.class).to(AsyncronEvent.class);

		bind(ChannelHandler.class).asEagerSingleton();

		bind(SendFailException.class);

		bind(MessagePattern.class).to(MessagePatternJSON.class);

		bind(Member.class).to(MemberImpl.class);

		bind(MemberHandler.class);

	}

	

}
