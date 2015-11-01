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

public class TexasHoldEmResultTests {

	@Test
	public void testHasStandardSettingsOnEmptyHand() {
		TexasHoldEmCalculator result = new TexasHoldEmCalculator(new ArrayList<Card>());
		Assert.assertEquals(result.getRankedResult(), TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD);
		Assert.assertEquals(result.getValueOfResult(), 0);
	}
	
	
	@DataProvider(name = "pairProvider")
	public Object[][] getPairHands() {
		Object[][] toReturn = new Object[VALUE.values().length][2];
		for(VALUE value : VALUE.values()) {
			Card card1 = new Card(SUITE.CLOVES, value);
			Card card2 = new Card(SUITE.DIAMONDS, value);
			ArrayList<Card> list = new ArrayList<Card>();
			list.add(card1);
			list.add(card2);
			Integer sum = new Integer(value.ordinal());
			toReturn[value.ordinal()][0] = list;
			toReturn[value.ordinal()][1] = sum;
		}
		return toReturn;
	}
	
	@Test(dataProvider = "pairProvider")
	public void testGivesCorrectResultsOnPairs(List<Card> cards, int value) {
		TexasHoldEmCalculator result = new TexasHoldEmCalculator(cards);
		Assert.assertEquals(result.getRankedResult(), TEXAS_HOLDEM_RANKED_RESULT.ONE_PAIR);
		Assert.assertEquals(result.getValueOfResult(), value);
	}
}
