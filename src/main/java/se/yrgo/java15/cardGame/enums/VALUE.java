package se.yrgo.java15.cardGame.enums;

public enum VALUE {
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN,
	JACK,
	QUEEEN,
	KING,
	ACE;
	
	public String toString() {
		return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toLowerCase();
	}

	public int getValue() {
		return this.ordinal() + 1;
	}
}
 