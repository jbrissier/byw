package de.jochenbrissier.backyard.core;

import com.google.inject.AbstractModule;

import de.jochenbrissier.backyard.module.WebsocketEvent;
import de.jochenbrissier.backyard.util.MessagePattern;
import de.jochenbrissier.backyard.util.MessagePatternJSON;
import de.jochenbrissier.backyard.util.SendFailException;

public class StandardModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Channel.class).to(ChannelImpl.class);

		bind(ChannelHandler.class).asEagerSingleton();

		bind(SendFailException.class);

		bind(MessagePattern.class).to(MessagePatternJSON.class);

		bind(Member.class).to(MemberImpl.class);

		bind(MemberHandler.class);

		
	}

}
