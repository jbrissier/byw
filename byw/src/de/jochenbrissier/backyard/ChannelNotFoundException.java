package de.jochenbrissier.backyard;
/**
 * will throw whenever a channel was not found
 * @author jochen
 *
 */
public class ChannelNotFoundException extends Exception {

	public ChannelNotFoundException() {
		super("Channel not found");

	}

}
