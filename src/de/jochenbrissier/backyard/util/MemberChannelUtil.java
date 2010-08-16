package de.jochenbrissier.backyard.util;

import java.util.ArrayList;
import java.util.List;

import de.jochenbrissier.backyard.core.Channel;
import de.jochenbrissier.backyard.core.ChannelHandler;
import de.jochenbrissier.backyard.core.Member;
import de.jochenbrissier.backyard.core.MemberHandler;

/**
 * Helps to translate member objects an translate the channel association
 * 
 * @author jochen
 * 
 */
public class MemberChannelUtil {

	ChannelHandler ch;
	MemberHandler mh;

	public MemberChannelUtil(ChannelHandler channelhandler,
			MemberHandler memberhandler) {

		this.ch = channelhandler;
		this.mh = memberhandler;

	}

	/**
	 * 
	 * returns the association channels to a member
	 * 
	 * 
	 * @param m
	 *            am Member obj
	 * @return
	 */
	public List<Channel> getMemberChannels(Member m) {
		// list which contains the channels which the member listen to
		ArrayList<Channel> memberlist = new ArrayList<Channel>();

		// gel all channels
		List<Channel> chl = ch.getAllChannel();

		// iterate all
		for (Channel channel : chl) {

			// if member is member of channel
			if (channel.isMember(m)) {
				memberlist.add(channel);
			}

		}

		// return the channels
		return memberlist;

	}

	/**
	 * Adds a member to a list of channels
	 * 
	 * @param channels
	 * @param m
	 */
	public void addMemberToChannels(List<Channel> channels, Member m) {
		for (Channel ch : channels) {

			ch.addMember(m);

		}
	}

}
