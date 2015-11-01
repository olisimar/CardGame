package se.yrgo.java15.cardGame.factories;

import java.util.HashSet;
import java.util.Set;

import se.yrgo.java15.cardGame.common.Card;
import se.yrgo.java15.cardGame.common.Deck;
import se.yrgo.java15.cardGame.enums.SUITE;
import se.yrgo.java15.cardGame.enums.VALUE;

public class ItemFactory {

	public static Set<Card> getFullDeckOfCards() {
		Set<Card> toReturn = new HashSet<Card>();
		for (SUITE suite : SUITE.values()) {
			for (VALUE value : VALUE.values()) {
				toReturn.add(new Card(suite, value));
			}
		}
		return toReturn;
	}
	
	public static Deck getStandardDeck() {
		return new Deck(ItemFactory.getFullDeckOfCards());
	}
}
