package de.jochenbrissier.backyard;

import java.util.LinkedList;

/**
 * If you want to extend backyard with a different implementation for the comet
 * event your should extend from this class.
 * 
 * @author jochenbrissier
 * 
 */
abstract class BackyardEvent implements Event {

	private static LinkedList<EventListener> evlist = new LinkedList<EventListener>();

	public static void addGlobalEventlistener(EventListener ev) {

		evlist.add(ev);

	}

	/**
	 * this should called from your implementation before the event will send
	 * the response back to the client
	 */
	public void onClose() {

		for (EventListener ev : evlist) {
			ev.onClose();
		}

	}

	/**
	 * this should called from your implementation until the event has an error
	 */
	public void onError() {
		for (EventListener ev : evlist) {
			ev.onError();
		}

	}

	/**
	 * this should called from your implementation before the processing ends
	 */
	public void onComplete() {
		for (EventListener ev : evlist) {
			ev.onComplete();
		}
	}

	/**
	 * this should called from your implementation before the processing starts
	 */
	public void onStart() {
		for (EventListener ev : evlist) {
			ev.onStart();
		}
	}

	/**
	 * this should called from your implementation before the processing has a
	 * timeout
	 */
	public void onTimeout() {
		for (EventListener ev : evlist) {
			ev.onTimeout();
		}
	}

}
