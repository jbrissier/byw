package de.jochenbrissier.backyard;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author jochenbrissier
 * 
 */

public class Backyard {

	Log log = LogFactory.getLog(Backyard.class);

	// static globals
	private static Injector in = Guice.createInjector(new BackyardModule());
	private static ChannelHandler channelhandler = in
			.getInstance(ChannelHandler.class);

	private static MemberHandler memberhandler = in
			.getInstance(MemberHandler.class);

	// members
	private Member member;
	private HttpServlet servlet;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	

	public Backyard(HttpServletRequest req, HttpServletResponse resp) {

		this.resp = resp;
		this.req = req;
		this.member = memberhandler.getMember(req.getSession().getId());

		listenToChannel(0);

	}

	// static methods

	public static void setAlternativeImpl(AbstractModule ba) {
		in = Guice.createInjector(ba);
	}
	
	public static void setAlternativeImpl(AbstractModule ba, AbstractModule ab) {
		in = Guice.createInjector(ba,ab);
		
	}
	
	
	public static void setAlternativeImpl(Iterable<? extends Module> ba) {
		in = Guice.createInjector(ba);
	}
	


	public void setMember(Member member) {
		this.member = member;
	}

	public HttpServlet getServlet() {
		return servlet;
	}

	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;

	}

	public static Channel getMetaChannel() {

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

	public static Channel addChannel(String name) {
		return channelhandler.getChannel(name);
	}

	public static void addChannelListener(String channel, ChannelListener cl) {
		channelhandler.getChannel(channel).addListener(cl);

	}

	public static void addChannelListener(int id, ChannelListener cl) {
		channelhandler.getChannel(id).addListener(cl);
	}

	public static void removeChannelListener(int id) {
		channelhandler.getChannel(id).addListener(null);
	}

	public static void removeChannelListener(String name) {
		channelhandler.getChannel(name).addListener(null);
	}

	public static void removeChannel(int id) {
		channelhandler.removeChannel(channelhandler.getChannel(id));
	}

	public static void removeChannel(String name) {
		channelhandler.removeChannel(channelhandler.getChannel(name));
	}

	public static Channel listentoChannel(String channel, Member member) {

		Channel cn = channelhandler.getChannel(channel);
		cn.addMember(member);
		return cn;
	}

	public static Channel listentoChannel(int id, Member member) {

		Channel cn = channelhandler.getChannel(id);
		cn.addMember(member);
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

	public Event startAsync(Object obj) {
		Event event = in.getInstance(Event.class);

		event.setEvent(obj);

		event.init(this.req, this.resp);
		this.member.setEvent(event);
		Member anMember = memberhandler
				.getMember(this.req.getSession().getId());
		anMember.setEvent(event);
		return event;

	}

	public void stopAsync() {
		stopAsync("na");

	}

	public void stopAsync(String message) {
		this.member.sendMessage(new Message(message));
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
