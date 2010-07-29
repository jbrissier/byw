package de.jochenbrissier.backyard.module;

import com.google.inject.AbstractModule;

import de.jochenbrissier.backyard.core.Event;

public class JettyModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Event.class).to(JettyEvent.class);

	}

}
