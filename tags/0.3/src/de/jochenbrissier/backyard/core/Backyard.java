package de.jochenbrissier.backyard.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.jochenbrissier.backyard.module.BackyardModule;
import de.jochenbrissier.backyard.module.WebsocketEvent;
import de.jochenbrissier.backyard.util.MemberChannelUtil;
import de.jochenbrissier.backyard.util.ServerDedec;
import de.jochenbrissier.backyard.util.ServerNotSupportedException;

/**
 * The main / entry point of Backyard. <br>
 *This class declares the main part of the API. <br>
 * <br>
 *How to ues: <br>
 *Backyard backyard = new Backyard(requeset,response); <br>
 * <br>
 * ba.startAsync(); <br>
 * <br>
 * To resume the async event. call <br>
 * <br>
 * ba.stopAsync(); <br>
 * <br>
 * Thats it.!! easy :)
 * 
 * @author jochenbrissier
 * 
 */

public class Backyard {

	Log log = LogFactory.getLog(Backyard.class);

	// static globals
	private static Injector in;
	public static ChannelHandler channelhandler;
	private static ArrayList<ChannelListenerBuffer> channelListenerBuffer = new ArrayList<ChannelListenerBuffer>();

	private static MemberHandler memberhandler;

	// members
	private Member member;
	private HttpServlet servlet;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private static boolean alternativ_impl = false;
	private static boolean websocketSupport = false;

	/**
	 *Constructor of Backyard.
	 * 
	 * @param req
	 *            a HttpServletRequest object
	 * @param resp
	 *            a HttpServeltRespons object
	 */

	public Backyard(HttpServletRequest req, HttpServletResponse resp) {

		this.resp = resp;
		this.req = req;
		
		if (!alternativ_impl)
			in = Guice.createInjector(new BackyardModule());

		channelhandler = in.getInstance(ChannelHandler.class);

		memberhandler = in.getInstance(MemberHandler.class);

		// load channelhandler from the buffer

		for (ChannelListenerBuffer key : channelListenerBuffer) {

			if (key.getChannel().matches(".*_nb")) {
				addChannelListener(new Integer(key.getChannel().split("_")[0]),
						key.getCl());
			} else {
				addChannelListener(key.getChannel(), key.getCl());
			}

		}
		// clear the buffer;

		channelListenerBuffer.clear();

		// get the member from the member handler.
		this.member = memberhandler.getMember(req.getSession().getId());

		// if member exist in meta channel replace member form channel handler
		// (IMPROVE)

		// listen to meta channel
		listenToChannel(0);

	}

	/**
	 * Functions detects the server on which backyard is running and configure
	 * some settings e.g. loading the specific module for the server supports
	 * the server websockets
	 * 
	 * @param servlet
	 */
	public static void autoDedectImpl(Servlet servlet) {
		try {

			// class to detect the running server
			ServerDedec det = new ServerDedec(servlet);

			// load injector
			in = Guice.createInjector(new StandardModule(), det
					.getModuleClass());

			// set alternativ_impl to true
			alternativ_impl = true;

			// set websocket support based on XML
			websocketSupport = det.getWebSocketSupport();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (ServerNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Override the Standard Module e.g for Channel, Member, ...
	 * 
	 * @param ba
	 */

	public static void setStandartImpel(AbstractModule ba) {
		in = Guice.createInjector(ba);
		alternativ_impl = true;
	}

	/**
	 * Backyard also provides a alternative implementations for a specific
	 * servlet container such as Tomcat, Jetty or Glassfish You can also write
	 * your own implementation for a other servlet container. <br>
	 * <br>
	 * 
	 * First you have to extend the class BackyardEvent and implement the
	 * methods. <br>
	 * 
	 * Second you have to write a second class and extend it with AbstractModule
	 * from google guice project. <br>
	 * In the module class you have to call
	 * bind(Event.class).to(Yourclass.class);
	 * 
	 * <br>
	 * 
	 * Third you have to invoke Backyard.setAlternativeImpl(new YourModule());
	 * 
	 * @param ba
	 */

	public static void setAlternativeImpl(AbstractModule ba) {
		in = Guice.createInjector(new StandardModule(), ba);
		alternativ_impl = true;
	}

	/**
	 * Backyard also provides a alternative implementations for a specific
	 * servlet container such as Tomcat, Jetty or Glassfish You can also write
	 * your own implementation for a other servlet container. <br>
	 * <br>
	 * 
	 * First you have to extend the class BackyardEvent and implement the
	 * methods. <br>
	 * 
	 * Second you have to write a second class and extend it with AbstractModule
	 * from google guice project. <br>
	 * In the module class you have to call
	 * bind(Event.class).to(Yourclass.class);
	 * 
	 * <br>
	 * 
	 * Third you have to invoke Backyard.setAlternativeImpl(new YourModule());
	 * 
	 * @param ba
	 */
	public static void setAlternativeImpl(AbstractModule ba, AbstractModule ab) {
		in = Guice.createInjector(new StandardModule(), ba, ab);
		alternativ_impl = true;

	}

	/**
	 * Backyard also provides a alternative implementations for a specific
	 * servlet container such as Tomcat, Jetty or Glassfish You can also write
	 * your own implementation for a other servlet container. <br>
	 * <br>
	 * 
	 * First you have to extend the class BackyardEvent and implement the
	 * methods. <br>
	 * 
	 * Second you have to write a second class and extend it with AbstractModule
	 * from google guice project. <br>
	 * In the module class you have to call
	 * bind(Event.class).to(Yourclass.class);
	 * 
	 * <br>
	 * 
	 * Third you have to invoke Backyard.setAlternativeImpl(new YourModule());
	 * 
	 * @param ba
	 */
	// TODO: IMPLEMENT THIS !
	/*
	 * public static void setAlternativeImpl(Iterable<? extends Module> ba) { in
	 * = Guice.createInjector(ba);
	 * 
	 * }
	 */

	/**
	 * set a other member to backyard obj. standart member is the member from
	 * which the request comes
	 * 
	 * @param member
	 */

	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * get servelt has in this version no effect
	 * 
	 * @return
	 */

	public HttpServlet getServlet() {
		return servlet;
	}

	/**
	 * set the servelt has in this version no effect
	 * 
	 * @return
	 */

	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;

	}

	/**
	 * Returns the meta channel The meta channel contains all members.
	 * 
	 * 
	 * 
	 * @return
	 */

	public static Channel getMetaChannel() {

		return channelhandler.getChannel("meta");

	}

	/**
	 * returns the member object
	 * 
	 * @return
	 */

	public Member getMember() {

		return memberhandler.getMember(this.req.getSession().getId());

	}

	/**
	 * let the current member, from which the request came listen to the a
	 * channel
	 * 
	 * @param name
	 * @return
	 */

	public Channel listenToChannel(String name) {

		Channel cn = channelhandler.getChannel(name);
		cn.addMember(this.member);

		return cn;

	}

	/**
	 * adds a channel to the queue
	 * 
	 * @param name
	 * @return
	 */

	public static Channel addChannel(String name) {
		return channelhandler.getChannel(name);
	}

	/**
	 * add a listener to a specific channel
	 * 
	 * @param channel
	 * @param cl
	 */

	public static void addChannelListener(String channel, ChannelListener cl) {

		if (channelhandler == null) {

			channelListenerBuffer.add(new ChannelListenerBuffer(channel, cl));
			return;

		}

		channelhandler.getChannel(channel).addListener(cl);

	}

	/**
	 * add a listener to a specific channel
	 * 
	 * @param channel
	 * @param cl
	 */

	public static void addChannelListener(int id, ChannelListener cl) {
		if (channelhandler == null) {

			channelListenerBuffer
					.add(new ChannelListenerBuffer(id + "_nb", cl));
			return;

		}

		channelhandler.getChannel(id).addListener(cl);
	}

	/**
	 * removes a channellistener from a channel
	 * 
	 * @param id
	 */

	public static void removeChannelListener(int id) {
		channelhandler.getChannel(id).addListener(null);
	}

	/**
	 * removes a channellistener from a channel
	 * 
	 * @param id
	 */

	public static void removeChannelListener(String name) {
		channelhandler.getChannel(name).addListener(null);
	}

	/**
	 * removes a channel from the queue
	 * 
	 * @param id
	 */

	public static void removeChannel(int id) {
		channelhandler.removeChannel(channelhandler.getChannel(id));
	}

	/**
	 * removes a channel from the queue
	 * 
	 * @param id
	 */

	public static void removeChannel(String name) {
		channelhandler.removeChannel(channelhandler.getChannel(name));
	}

	/**
	 * lets a specific member listen to a channel
	 * 
	 * @param channel
	 * @param member
	 * @return
	 */

	public static Channel listentoChannel(String channel, Member member) {

		Channel cn = channelhandler.getChannel(channel);
		cn.addMember(member);
		return cn;
	}

	/**
	 * lets a specific member listen to a channel
	 * 
	 * @param channel
	 * @param member
	 * @return
	 */

	public static Channel listentoChannel(int id, Member member) {

		Channel cn = channelhandler.getChannel(id);
		cn.addMember(member);
		return cn;
	}

	/**
	 * lets a the member, from which the request came listen to a channel
	 * 
	 * @param channel
	 * @param member
	 * @return
	 */

	public Channel listenToChannel(int id) {
		Channel cn = channelhandler.getChannel(id);
		cn.addMember(this.member);

		return cn;

	}

	/**
	 * stops the current req -> res processing until a message will send to the
	 * member or the function stopAsync will called
	 * 
	 * 
	 * 
	 * @return
	 */
	public Event startAsync() {

		Event event = in.getInstance(Event.class);
		event.init(this.req, this.resp);
		this.member.setEvent(event);
		Member anMember = memberhandler
				.getMember(this.req.getSession().getId());
		anMember.setEvent(event);
		return event;

	}

	/**
	 * stops the current req -> res processing until a message will send to the
	 * member or the function stopAsync will called
	 * 
	 * if this function will called it will give the BackyardEvent implmentation
	 * the given obj.
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */

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

	/**
	 * stops the async prossesing
	 */

	public void stopAsync() {
		stopAsync("na");

	}

	/**
	 * sops the asyn prossesing and will send the message to the member
	 * 
	 * @param message
	 */

	public void stopAsync(String message) {
		this.member.sendMessage(new Message(message));
	}

	/***
	 * returns a channel. if the channel not exists it will creat it
	 * 
	 * @param name
	 * @return
	 */

	public Channel getChannel(String name) {
		return channelhandler.getChannel(name);
	}

	/**
	 * 
	 * @return true if the member has a comet event.
	 */

	public boolean hasEvent() {
		return getMetaChannel().hasEvent(this.member);
	}

	/**
	 * 
	 * @param member
	 * @return return true if the given member has an event
	 */
	public boolean hasEvent(Member member) {

		return getMetaChannel().isMember(member.getMemberlId());

	}

	/**
	 * functions checks if the current server supports websockets
	 * 
	 * @return
	 */
	public static boolean isWebsocketSupport() {
		return websocketSupport;
	}

	/**
	 * function creates a new Websocket if the container supports it
	 * 
	 * @return
	 */
	public static WebsocketEvent getSocket() {

		if (isWebsocketSupport()) {
			Injector inj = Guice.createInjector(new WebsocketModule());

			return inj.getInstance(WebsocketEvent.class);
		}
		// TODO: EXCPTION
		return null;
	}

	public static Member getSocketMember(HttpServletRequest req) {

		// try to find a existing user
		// get session id
		String id = req.getSession().getId();

		Member member = null;
		member = memberhandler.getMember(id);

		if (member instanceof WebSocketMember)
			return member;

		// create a new member
		Member wsmember = new WebSocketMember(Backyard.getSocket());
		wsmember.setMemberId(id);

		MemberChannelUtil mcu = new MemberChannelUtil(channelhandler,
				memberhandler);

		List<Channel> ch = mcu.getMemberChannels(member);

		mcu.addMemberToChannels(ch, wsmember);
		
		
		memberhandler.replaceMember(wsmember);

		// tranclate the channels from old to new member obj

		return wsmember;
	}

}
