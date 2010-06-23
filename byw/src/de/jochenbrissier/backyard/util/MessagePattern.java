package de.jochenbrissier.backyard.util;

import java.util.Collection;

import de.jochenbrissier.backyard.core.Message;

/**
 * 
 * interface of message pattern
 * 
 * 
 * it returns a message string which will send to the client.
 * 
 * @author jochen
 * 
 */

public interface MessagePattern {

	public String getMessages(Collection<Message> messages);

}