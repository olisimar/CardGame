package se.yrgo.java15.cardGame.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import junit.framework.Assert;
import se.yrgo.java15.cardGame.common.Card;
import se.yrgo.java15.cardGame.common.Deck;
import se.yrgo.java15.cardGame.exceptions.OutOfCardsException;
import se.yrgo.java15.cardGame.factories.ItemFactory;

public class DeckTests {

	@Test
	public void testThatACardCanBeDealt() {
		Deck deck = new Deck(ItemFactory.getFullDeckOfCards());
		Card card = deck.getTopCard();
		Assert.assertTrue(card != null);
	}
	
	@Test
	public void testRightAmountOfCardsAreReturned() {
		Deck deck = ItemFactory.getStandardDeck();
		List<Card> list = deck.getCards(5);
		Assert.assertEquals(5, list.size());
	}
	
	@Test(expectedExceptions={OutOfCardsException.class})
	public void testRunningOutOfCardsGeneratesException() {
		Deck deck = ItemFactory.getStandardDeck();
		int runner = 60;
		while(runner > 0) {
			deck.getTopCard();
			runner--;
		}
	}
	
	@Test
	public void testThatTwoUnshuffledDecksHaveSameOrder() {
		Deck one = ItemFactory.getStandardDeck();
		Deck two = ItemFactory.getStandardDeck();
		while(one.hasOneMoreCard()) {
			Assert.assertEquals(one.getTopCard(), two.getTopCard());
		}
	}
	
	@Test
	public void testCardOrderAfterShufflingAreNotSame() {
		Deck one = ItemFactory.getStandardDeck();
		one.shuffle();
		Deck two = ItemFactory.getStandardDeck();
		two.shuffle();
		List<Card> oneOrder = new ArrayList<Card>();
		List<Card> twoOrder = new ArrayList<Card>();
		while(one.hasOneMoreCard()) {
			oneOrder.add(one.getTopCard());
			twoOrder.add(two.getTopCard());
		}
		Assert.assertFalse(oneOrder.equals(twoOrder));
	}
}
 