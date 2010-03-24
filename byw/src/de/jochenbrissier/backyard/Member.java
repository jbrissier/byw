package de.jochenbrissier.backyard;

import java.io.PrintWriter;

import org.apache.catalina.CometEvent;

/**
 * represtation of an backyard member members will be seperatet by there id.
 * 
 * 
 * @author jochen
 * 
 */

public interface Member {

	public abstract Event getEvent();

	public abstract void setEvent(Event ev);

	public abstract String getMemberName();

	public abstract void setMemberName(String name);

	public abstract String getMemberlId();

	public abstract void setMemberId(String id);

	public abstract long getTimestamp();

	public boolean equals(Object o);

	void sendMessage(Message message);

	void setLastMessage(Message message);

	Message getLastMessage();

}