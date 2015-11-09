package se.yrgo.java15.cardGame.ruleSets;

import java.util.List;

import se.yrgo.java15.cardGame.common.Card;
import se.yrgo.java15.cardGame.enums.TEXAS_HOLDEM_RANKED_RESULT;
import se.yrgo.java15.cardGame.enums.VALUE;

public class ResultingHand {


	private List<Card> cards;
	private TEXAS_HOLDEM_RANKED_RESULT hand = TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD;  // safe default
	private int value = 0;

	public ResultingHand(TEXAS_HOLDEM_RANKED_RESULT hand, List<Card> cards) {
		this.hand = hand;
		this.cards = cards;
		this.value = calculateValue(hand, cards);
	}

	private int calculateValue(TEXAS_HOLDEM_RANKED_RESULT hand, List<Card> cards) {
		int toReturn = 0;
		if((hand == TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT) || (hand == TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT_FLUSH)) {
			boolean hasAce = false;
			boolean hasTwo = false;
			for(Card card : cards) {
				toReturn += card.getValue().getValue();
				if(card.getValue() == VALUE.ACE) {
					hasAce = true;
				}
				if(card.getValue() == VALUE.TWO) {
					hasTwo = true;
				}
			}
			if(hasAce & hasTwo) {
				toReturn = 15;
			}
		} else {
			for(Card card : cards) {
				toReturn += card.getValue().getValue();
			}
		}
		return toReturn;
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

	public void setValue() {

	}
}
