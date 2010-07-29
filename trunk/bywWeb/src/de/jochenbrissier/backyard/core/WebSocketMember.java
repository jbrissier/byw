package de.jochenbrissier.backyard.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;

import de.jochenbrissier.backyard.module.WebsocketEvent;

public class WebSocketMember implements Member {
	private Log log = LogFactory.getLog(WebsocketEvent.class);

	WebsocketEvent event;

	private String name;
	private String id;
	private long timestamp;
	private Message lastMessage;

	private Queue<Message> messages = new ConcurrentLinkedQueue<Message>();

	@Inject
	public WebSocketMember(WebsocketEvent event) {
		this.event = event;

	}

	@Override
	public Event getEvent() {
		// TODO Auto-generated method stub
		return event;
	}

	@Override
	public Message getLastMessage() {
		// TODO Auto-generated method stub
		return messages.element();
	}

	@Override
	public String getMemberName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMemberlId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public long getTimestamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendMessage(Message message) {
		// add messages
		messages.add(message);
		// send messages
		flush();
	}

	@Override
	public void setEvent(Event ev) {

	}

	@Override
	public void setLastMessage(Message message) {
		messages.add(message);
	}

	@Override
	public void setMemberId(String id) {
		this.id = id;
	}

	@Override
	public void setMemberName(String name) {
		this.name = name;

	}

	private void flush() {
		// if the event is ready and has no errors
		if (!event.isError() && event.isReady()) {

			// iterate the messages
			for (Message mes : messages) {
				// send message over event here: Websocket
				event.addMessage(mes);
			}

			messages.clear();
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof WebSocketMember) {

			Member m = (Member) obj;

			return this.id.equals(m.getMemberlId());

		}

		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
