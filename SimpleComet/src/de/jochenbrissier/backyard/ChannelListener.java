package de.jochenbrissier.backyard;




public interface ChannelListener {

	/**
	 * Invokes if you add ChannelListener to a channel IMPORTANT return a member
	 * ob!! if you return null the member will not add to the channel so for instance you can simply protect your channel 
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
