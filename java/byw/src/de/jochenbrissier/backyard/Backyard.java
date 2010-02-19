package de.jochenbrissier.backyard;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author jochenbrissier
 * 
 */

public class Backyard {

	Log log = LogFactory.getLog(Backyard.class);

	public Backyard(HttpServletRequest req, HttpServletResponse resp) {

		this.resp = resp;
		this.req = req;
		this.member = memberhandler.getMember(req.getSession().getId());

		listenToChannel(0);

	}

	private Member member;
	private HttpServlet servlet;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private static Injector in = Guice.createInjector(new BackyardModule());
	private static ChannelHandler channelhandler = in
			.getInstance(ChannelHandler.class);

	
	
	
	private static MemberHandler memberhandler = in
			.getInstance(MemberHandler.class);

	public void setMember(Member member) {
		this.member = member;
	}

	public HttpServlet getServlet() {
		return servlet;
	}

	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;

	}

	public Channel getMetaChannel() {

		return channelhandler.getChannel(0);

	}

	public Member getMember() {

		return memberhandler.getMember(this.req.getSession().getId());

	}

	public Channel listenToChannel(String name) {

		Channel cn = channelhandler.getChannel(name);
		cn.addMember(this.member);

		return cn;

	}

	public Channel listenToChannel(int id) {
		Channel cn = channelhandler.getChannel(id);
		cn.addMember(this.member);

		return cn;

	}

	public Event startAsync() {

		Event event = in.getInstance(Event.class);
		event.init(this.req, this.resp);
		this.member.setEvent(event);
		Member anMember = memberhandler
				.getMember(this.req.getSession().getId());
		anMember.setEvent(event);
		return event;

	}

	public Channel getChannel(String name) {
		return channelhandler.getChannel(name);
	}

	public boolean hasEvent() {
		return getMetaChannel().hasEvent(this.member);
	}

	public boolean hasEvent(Member member) {

		return getMetaChannel().isMember(member.getMemberlId());

	}

}
