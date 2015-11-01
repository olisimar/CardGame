package se.yrgo.java15.cardGame.ruleSets;

import java.util.ArrayList;
import java.util.List;

import se.yrgo.java15.cardGame.common.Card;
import se.yrgo.java15.cardGame.enums.TEXAS_HOLDEM_RANKED_RESULT;

public class ResultingHand {
	

	private List<Card> cards;
	private TEXAS_HOLDEM_RANKED_RESULT hand = TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD;  // safe default
	private int value = 0;

	public ResultingHand(int value, TEXAS_HOLDEM_RANKED_RESULT hand) {
		this(value, hand, new ArrayList<Card>());
	}
	
	public ResultingHand(int value, TEXAS_HOLDEM_RANKED_RESULT hand, List<Card> cards) {
		this.hand = hand;
		this.value = value;
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public TEXAS_HOLDEM_RANKED_RESULT getHand() {
		return hand;
	}

	public int getValue() {
		return value;
	}
}
