package de.jochenbrissier.backyard;

public interface ChannelListener {

	/**
	 * Invokes if you add ChannelListener to a channel IMPORTANT return a member
	 * ob!!
	 * 
	 * @param member
	 * @return
	 */
	public Member newMember(Member member);

	/**
	 * Will be invoke if you add a ChannelListener to a channel
	 * 
	 * @param message
	 * @return
	 */

	public String newMessage(String message);

}
