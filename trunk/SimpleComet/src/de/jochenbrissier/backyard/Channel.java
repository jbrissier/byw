package de.jochenbrissier.backyard;

import java.util.Collection;

public interface Channel {

	/**
	 *Add member to channel
	 * 
	 */

	public void addMember(Member member);

	/**
	 * send message to all members
	 * 
	 * @param message
	 */
	public void sendMessage(Message message);

	
	public void sendMessage(String data);
	
	
	
	/**
	 * 
	 * returns the channel members
	 * 
	 * 
	 * @return
	 */

	public Collection<Member> getMembers();

	/**
	 * 
	 * 
	 * Removes a channel member
	 * 
	 * @param member
	 */

	public void removeMember(Member member);

	/**
	 * removes all channel members
	 * 
	 * 
	 */

	public void ClearMembers();

	// setter & getters for channel name and channel id

	public String getChannelName();

	public long getChannelId();

	public void setChannelId(long id);

	public void setChannelName(String name);

	public boolean isMember(String id);

	public boolean isMember(Member member);
	
	public boolean hasEvent(Member member);
	
	
	public void addListener(ChannelListener cL);

}
