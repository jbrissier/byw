package de.jochenbrissier.backyard;

public class SendFailException extends Exception {

	public SendFailException() {
		super("Message send Fails");

	}

	SendFailException(String Message) {
		super("Message send Fails: " + Message);

	}

}
