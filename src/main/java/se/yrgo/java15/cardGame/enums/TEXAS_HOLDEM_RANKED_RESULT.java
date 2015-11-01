package se.yrgo.java15.cardGame.enums;

public enum TEXAS_HOLDEM_RANKED_RESULT {

	HIGH_CARD,
	ONE_PAIR,
	TWO_PAIR,
	THREE_OF_A_KIND,
	STRAIGHT,			// Any suite, in order
	FLUSH,				// Any cards, same suite.
	FULL_HOUSE,			// Three of a kind + a pair
	FOUR_OF_A_KIND,		// Any of same rank.
	STRAIGHT_FLUSH,		// Same suite, in order
	ROYAL_FLUSH;		// Same suite, 10 to ace
}
