package se.yrgo.java15.cardGame.enums;

public enum SUITE {
	DIAMONDS,
	CLUBS,
	HEARTS,
	SPADES;

	@Override
	public String toString() {
		return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toLowerCase();
	}
}
