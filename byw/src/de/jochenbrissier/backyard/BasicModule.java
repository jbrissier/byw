package de.jochenbrissier.backyard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
/**
 * 
 * this is the standard binding of the interfaces
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

public class BasicModule extends AbstractModule {

	Log log = LogFactory.getLog(BasicModule.class);

	protected String serverInfo;

	@Override
	protected void configure() {

		// bind(ChannelHandler.class).in(Singleton.class);
		// bind(Channel.class).to(AbstractChannel.class);
		// bind(Member.class).to(MemberImpl.class);
		// bind(Backyard.class).in(Singleton.class);
		// bind(Meta.class).in(Singleton.class);
		// bind(TomcatServlet.class).in(Singleton.class);
		// bind(Event.class).to(EventImpl.class);

		// bind channel
		bind(Channel.class).to(ChannelImpl.class);

		// bind MemberHanlder
		bind(MemberHandler.class).asEagerSingleton();
		
		bind(ChannelHandler.class).asEagerSingleton();

		bind(SendFailException.class);

		bind(Member.class).to(MemberImpl.class);

	}

}
