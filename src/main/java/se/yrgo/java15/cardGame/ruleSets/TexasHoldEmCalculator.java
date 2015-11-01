package se.yrgo.java15.cardGame.ruleSets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import se.yrgo.java15.cardGame.common.Card;
import se.yrgo.java15.cardGame.enums.SUITE;
import se.yrgo.java15.cardGame.enums.TEXAS_HOLDEM_RANKED_RESULT;
import se.yrgo.java15.cardGame.enums.VALUE;

public class TexasHoldEmCalculator {

	private List<Card> cards;
	private TEXAS_HOLDEM_RANKED_RESULT bestHand = TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD;  // safe default
	private List<Card> resultingCards = new ArrayList<Card>();
	private Set<ResultingHand> results = new HashSet<ResultingHand>();
	private int resultValue = 0;

	public TexasHoldEmCalculator(List<Card> cards) {
		this.cards = cards;
		this.evaluateHand(cards);
	}
	
	protected void evaluateHand(List<Card> cards) {
		TreeMap<VALUE, List<Card>> ranks = this.getValueCombinations(cards);
		TreeMap<SUITE, List<Card>> suites = this.getSuiteCombinations(cards);
		List<Card> onePair = this.findHighestSet(cards, 2);
		if(!onePair.isEmpty()) {
			this.results.add(new ResultingHand(onePair.get(0).getValue().getValue(), TEXAS_HOLDEM_RANKED_RESULT.ONE_PAIR, onePair));
		}
		List<Card> tripple = this.findHighestSet(cards, 3);
		List<Card> quattro = this.findHighestSet(cards, 4);
		List<Card> house = null;
		List<Card> suite = null;
		List<Card> straight = null;
		List<Card> strightFlush = null;
		List<Card> royalStright = null;
		
		if(onePair != null) {
			this.resultingCards = onePair;
			this.resultValue = this.resultingCards.get(0).getValue().ordinal();
			this.bestHand = TEXAS_HOLDEM_RANKED_RESULT.ONE_PAIR;
		}
	}

	public List<Card> getInitialCards() {
		return this.cards;
	}
	
	public TEXAS_HOLDEM_RANKED_RESULT getRankedResult() {
		return this.bestHand;
	}
	
	public int getValueOfResult() {
		return this.resultValue;
	}
	
	protected List<Card> findHighestCard(List<Card> cards) {
		return cards;
	}
	
	protected List<Card> findHighestSet(List<Card> cards, int amount) {
		List<Card> toReturn = null;
		int value = 0;
		for(Entry<VALUE, List<Card>> result : this.getValueCombinations(cards).entrySet()) {
			if(result.getValue().size() == amount) {
				if(toReturn == null) {
					toReturn = result.getValue();
					value = result.getKey().ordinal();
				} else if(result.getKey().ordinal() < value) {
					toReturn = result.getValue();
					value = result.getKey().ordinal();
				}
			}
		}
		return toReturn;
	}
	
	protected TreeMap<VALUE, List<Card>> getValueCombinations(List<Card> cards) {
		TreeMap<VALUE, List<Card>> result = new TreeMap<VALUE, List<Card>>();
		
		for(Card card : cards) {
			List<Card> list = result.get(card.getValue());
			if(list == null) {
				list = new ArrayList<Card>();
			}
			list.add(card);
			result.put(card.getValue(), list);
		}
		return result;
	}
	
	private TreeMap<SUITE, List<Card>> getSuiteCombinations(List<Card> cards) {
		TreeMap<SUITE, List<Card>> result = new TreeMap<SUITE, List<Card>>();
		
		for(Card card : cards) {
			List<Card> list = result.get(card.getSuite());
			if(list == null) {
				list = new ArrayList<Card>();
			}
			list.add(card);
			result.put(card.getSuite(), list);
		}
		return result;
	}

}
