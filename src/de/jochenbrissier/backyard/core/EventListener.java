package de.jochenbrissier.backyard.core;
/**
 * if you want to listen to  specific event you should add it to the event
 * if you want to listen to all events you should add it to BackyardEvent.addGlobalEvent(ev);
 * @author jochen
 *
 */
public interface EventListener {

	void onTimeout();

	void onClose();

	void onStart();

	void onError();
	
	void onComplete();

}
