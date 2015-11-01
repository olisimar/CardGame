package se.yrgo.java15.cardGame.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import se.yrgo.java15.cardGame.common.Deck;
import se.yrgo.java15.cardGame.common.Player;
import se.yrgo.java15.cardGame.factories.ItemFactory;

public class PlayerTests {

	@Test
	public void testPlayerWithTooLongNameGetsShortened() {
		Player player = new Player("0123456789abcdefghij");
		Assert.assertEquals(player.getName().length(), 15);
		Assert.assertEquals(player.getName(), "0123456789abcde");
	}
	
	@Test
	public void testPlayerHasExpectedAmountOfCards() {
		Player player = new Player("Kalle");
		Deck deck = ItemFactory.getStandardDeck();
		player.acceptCard(deck.getTopCard());
		Assert.assertEquals(player.getCards().size(), 1);
	}
	
	@Test
	public void testPlayerHasExpectedHand() {
		Player player = new Player("Kalle");
		Deck deck = ItemFactory.getStandardDeck();
		for(int i=0;i > 5; i++) {
			player.acceptCard(deck.getTopCard());
		}
		Assert.assertEquals(player.getCards().size(), 5);
		Assert.assertEquals(player.getCards(), deck.getDealtCards());
	}
}
