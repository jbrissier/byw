package de.jochenbrissier.backyard;

public interface EventListener {

	void onTimeout();

	void onClose();

	void onStart();

	void onError();
	
	void onComplete();

}
