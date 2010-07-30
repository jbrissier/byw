package de.jochenbrissier.backyard.core;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * manages the members
 * 
 * @author jochen
 * 
 */
@Singleton
public class MemberHandler {

	// TODO: THREAD SAVE IMPROVEMENTS!!!

	Log log = LogFactory.getLog(MemberHandler.class);

	LinkedList<Member> member = new LinkedList<Member>();
	Injector in;

	@Inject
	public MemberHandler(Injector in) {

		this.in = in;

	}

	public Member getMember(String sessionId) {
		log.debug("get member: " + member);
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

	/**
	 * removes a member from the handler
	 * 
	 * @param sessionId
	 */
	public void removeMember(String sessionId) {
		// member to remove
		ArrayList<Member> rm = new ArrayList<Member>();

		synchronized (member) {

			for (Member m : member) {
				// if member found session id equals id
				if (m.getMemberlId().equals(sessionId))
					rm.add(m);

			}

		}

		// remove member
		synchronized (member) {

			for (Member m : rm) {
				if (rm != null)
					member.remove(rm);
			}
		}

	}

	public void addMember(Member mem) {
		synchronized (member) {
			member.add(mem);
		}
	}

	public void replaceMember(Member mem) {

		synchronized (member) {
			for (int i = 0; i < member.size(); i++) {
				if (member.get(i).equals(mem)) {
					member.set(i, mem);
				}

			}

		}

	}

}
