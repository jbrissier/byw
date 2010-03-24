package de.jochenbrissier.backyard;

import java.util.Collection;

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