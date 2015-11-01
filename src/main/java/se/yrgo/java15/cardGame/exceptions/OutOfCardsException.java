package se.yrgo.java15.cardGame.exceptions;

public class OutOfCardsException extends RuntimeException {

	/**/
	private static final long serialVersionUID = -4915040243795389984L;

	public OutOfCardsException(String message) {
		super(message);
	}
}
