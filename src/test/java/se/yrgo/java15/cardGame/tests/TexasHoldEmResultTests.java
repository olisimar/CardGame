package se.yrgo.java15.cardGame.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import se.yrgo.java15.cardGame.common.Card;
import se.yrgo.java15.cardGame.enums.SUITE;
import se.yrgo.java15.cardGame.enums.TEXAS_HOLDEM_RANKED_RESULT;
import se.yrgo.java15.cardGame.enums.VALUE;
import se.yrgo.java15.cardGame.ruleSets.TexasHoldEmCalculator;

@Test(singleThreaded = true)
public class TexasHoldEmResultTests {

	@Test
	public void testHasStandardSettingsOnEmptyHand() {
		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(new ArrayList<Card>());
		Assert.assertEquals(calc.getResults().size(), 1);
	}


	@DataProvider(name = "pairProvider")
	public Object[][] getPairHands() {
		Object[][] toReturn = new Object[VALUE.values().length][2];
		for(VALUE value : VALUE.values()) {
			Card card1 = new Card(SUITE.CLUBS, value);
			Card card2 = new Card(SUITE.DIAMONDS, value);
			ArrayList<Card> list = new ArrayList<Card>();
			list.add(card1);
			list.add(card2);
			Integer sum = new Integer(value.getValue() * 2);
			toReturn[value.ordinal()][0] = list;
			toReturn[value.ordinal()][1] = sum;
		}
		return toReturn;
	}

	@Test(dataProvider = "pairProvider")
	public void testGivesCorrectResultsOnPairs(List<Card> cards, int value) {
		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.ONE_PAIR);
		Assert.assertEquals(calc.getBestResult().getValue(), value);
	}

	@DataProvider(name = "trippleProvider")
	public Object[][] getTrippleHands() {
		Object[][] toReturn = new Object[VALUE.values().length][2];
		for(VALUE value : VALUE.values()) {
			Card card1 = new Card(SUITE.CLUBS, value);
			Card card2 = new Card(SUITE.DIAMONDS, value);
			Card card3 = new Card(SUITE.HEARTS, value);
			ArrayList<Card> list = new ArrayList<Card>();
			list.add(card1);
			list.add(card2);
			list.add(card3);
			Integer sum = new Integer(value.getValue() * 3);
			toReturn[value.ordinal()][0] = list;
			toReturn[value.ordinal()][1] = sum;
		}
		return toReturn;
	}

	@Test(dataProvider = "trippleProvider")
	public void testGivesCorrectResultsOnTripples(List<Card> cards, int value) {
		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getResults().size(), 2);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.THREE_OF_A_KIND);
		Assert.assertEquals(calc.getBestResult().getValue(), value);
	}

	@DataProvider(name = "FourOfAKindProvider")
	public Object[][] getFourOfAKind() {
		Object[][] toReturn = new Object[VALUE.values().length][2];
		for(VALUE value : VALUE.values()) {
			Card card1 = new Card(SUITE.CLUBS, value);
			Card card2 = new Card(SUITE.DIAMONDS, value);
			Card card3 = new Card(SUITE.HEARTS, value);
			Card card4 = new Card(SUITE.SPADES, value);
			ArrayList<Card> list = new ArrayList<Card>();
			list.add(card1);
			list.add(card2);
			list.add(card3);
			list.add(card4);
			Integer sum = new Integer(value.getValue() * 4);
			toReturn[value.ordinal()][0] = list;
			toReturn[value.ordinal()][1] = sum;
		}
		return toReturn;
	}

	@Test(dataProvider = "FourOfAKindProvider")
	public void testGivesCorrectResultsOnFourOfAKind(List<Card> cards, int value) {
		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getResults().size(), 3);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.FOUR_OF_A_KIND);
	}

	@Test
	public void testProvidedAFullHouseFindsIt() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.THREE));
		cards.add(new Card(SUITE.DIAMONDS, VALUE.THREE));
		cards.add(new Card(SUITE.HEARTS, VALUE.THREE));
		cards.add(new Card(SUITE.CLUBS, VALUE.FIVE));
		cards.add(new Card(SUITE.SPADES, VALUE.FIVE));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getResults().size(), 4);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.FULL_HOUSE);
	}

	@Test
	public void testFindProvidedStraight_generic() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.THREE));
		cards.add(new Card(SUITE.DIAMONDS, VALUE.FOUR));
		cards.add(new Card(SUITE.HEARTS, VALUE.SIX));
		cards.add(new Card(SUITE.CLUBS, VALUE.FIVE));
		cards.add(new Card(SUITE.SPADES, VALUE.SEVEN));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT);
		Assert.assertEquals(calc.getBestResult().getValue(), 25);
	}

	@Test(singleThreaded = true)
	public void testFindProvidedStraight_aceLow() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.TWO));
		cards.add(new Card(SUITE.SPADES, VALUE.ACE));
		cards.add(new Card(SUITE.CLUBS, VALUE.THREE));
		cards.add(new Card(SUITE.DIAMONDS, VALUE.FOUR));
		cards.add(new Card(SUITE.HEARTS, VALUE.FIVE));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT);
		Assert.assertEquals(calc.getBestResult().getValue(), 15);
	}

	@Test
	public void testFindProvidedStraight_aceHigh() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.TEN));
		cards.add(new Card(SUITE.DIAMONDS, VALUE.JACK));
		cards.add(new Card(SUITE.HEARTS, VALUE.QUEEEN));
		cards.add(new Card(SUITE.CLUBS, VALUE.KING));
		cards.add(new Card(SUITE.SPADES, VALUE.ACE));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT);
		Assert.assertEquals(calc.getBestResult().getValue(), 60);
	}

	@Test
	public void testFindProvidedStraightFlush_generic() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.THREE));
		cards.add(new Card(SUITE.CLUBS, VALUE.FOUR));
		cards.add(new Card(SUITE.CLUBS, VALUE.SIX));
		cards.add(new Card(SUITE.CLUBS, VALUE.FIVE));
		cards.add(new Card(SUITE.CLUBS, VALUE.EIGHT));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.FLUSH);
		Assert.assertEquals(calc.getBestResult().getValue(), 26);
	}

	@Test
	public void testFindProvidedRoyalFlush_aceHigh() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.TEN));
		cards.add(new Card(SUITE.CLUBS, VALUE.JACK));
		cards.add(new Card(SUITE.CLUBS, VALUE.QUEEEN));
		cards.add(new Card(SUITE.CLUBS, VALUE.KING));
		cards.add(new Card(SUITE.CLUBS, VALUE.ACE));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.ROYAL_FLUSH);
		Assert.assertEquals(calc.getBestResult().getValue(), 60);
	}
	
	@Test
	public void testFindBestStraight_sevenCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(SUITE.CLUBS, VALUE.EIGHT));
		cards.add(new Card(SUITE.CLUBS, VALUE.NINE));
		cards.add(new Card(SUITE.CLUBS, VALUE.TEN));
		cards.add(new Card(SUITE.CLUBS, VALUE.JACK));
		cards.add(new Card(SUITE.CLUBS, VALUE.QUEEEN));
		cards.add(new Card(SUITE.CLUBS, VALUE.KING));
		cards.add(new Card(SUITE.CLUBS, VALUE.ACE));

		TexasHoldEmCalculator calc = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(calc.getBestResult().getHand(), TEXAS_HOLDEM_RANKED_RESULT.ROYAL_FLUSH);
		Assert.assertEquals(calc.getBestResult().getValue(), 60);
	}
}
