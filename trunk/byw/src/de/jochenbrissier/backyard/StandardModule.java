package de.jochenbrissier.backyard;

import com.google.inject.AbstractModule;

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
