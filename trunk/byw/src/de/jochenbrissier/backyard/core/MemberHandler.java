package de.jochenbrissier.backyard.core;

import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * manages the members 
 * @author jochen
 *
 */
@Singleton
public class MemberHandler {
	Log log = LogFactory.getLog(MemberHandler.class);

	LinkedList<Member> member = new LinkedList<Member>();
	Injector in;

	@Inject
	public MemberHandler(Injector in) {

		this.in = in;

	}

	public Member getMember(String sessionId) {
		log.debug("get member: "+ member);
		synchronized (member) {

			for (Member m : member) {
				if (m.getMemberlId().equals(sessionId))
					return m;
			}
			log.debug("new member");
			Member mem = in.getInstance(Member.class);
			mem.setMemberId(sessionId);
			member.add(mem);
			return mem;

		}

	}

}
