package se.yrgo.java15.cardGame.enums;

public enum VALUE {
	TWO(2,2),
	THREE(3,3),
	FOUR(4,4),
	FIVE(5,5),
	SIX(6,6),
	SEVEN(7,7),
	EIGHT(8,8),
	NINE(9,9),
	TEN(10,10),
	JACK(11,10),
	QUEEN(12,10),
	KING(13,10),
	ACE(14,1);

	private int value;
	private int altValue;

	VALUE(int value, int altValue) {
		this.value = value;
		this.altValue = altValue;
	}

	@Override
	public String toString() {
		return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toLowerCase();
	}

	public int getValue() {
		return value;
	}
	public int getAltValue() {
		return altValue;
	}
}
