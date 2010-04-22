package de.jochenbrissier.backyard;

import com.google.inject.AbstractModule;

public class JettyModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Event.class).to(JettyEvent.class);

	}

}
