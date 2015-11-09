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
	private Set<ResultingHand> results = new HashSet<ResultingHand>();

	public TexasHoldEmCalculator(List<Card> cards) {
		this.cards = cards;
		this.evaluateHand(cards);
	}

	protected void evaluateHand(List<Card> cards) {
		this.results = new HashSet<ResultingHand>();
		this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD, cards));

		List<Card> onePair = this.findHighestSet(cards, 2);
		if(!onePair.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.ONE_PAIR, onePair));
		}
		List<Card> twoPair = this.findTwoPair(cards);
		if(twoPair.size() == 4) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.TWO_PAIR, twoPair));
		}
		List<Card> tripple = this.findHighestSet(cards, 3);
		if(!tripple.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.THREE_OF_A_KIND, tripple));
		}
		List<Card> flush = this.findFlush(cards);
		if(flush.size() == 5) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.FLUSH, flush));
		}
		List<Card> straight = this.findStraight(cards);
		if(straight.size() == 5) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT, straight));
			System.out.println(this.getBestResult().getHand());
		} else {
			System.out.println("No straight with "+ straight);
		}
		List<Card> house = this.findHouse(cards);
		if(!house.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.FULL_HOUSE, house));
		}
		List<Card> quattro = this.findHighestSet(cards, 4);
		if(!quattro.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.FOUR_OF_A_KIND, quattro));
		}
		if((straight.size() == 5) && (flush.size() == 5)) {
			boolean royal = false;
			for(Card card : cards) {
				if(card.getValue() == VALUE.ACE) {
					this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.ROYAL_FLUSH, flush));
					royal = true;
					break;
				}
			}
			if(!royal) {
				this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT_FLUSH, flush));
			}
		}
	}

	private List<Card> findStraight(List<Card> cards) {
		ArrayList<Card> toReturn = new ArrayList<Card>();
		TreeMap<VALUE, List<Card>> sorted = this.getValueCombinations(cards);
		if(sorted.size() == 5) {
			int previousValue = 0;
			for(VALUE value : sorted.descendingKeySet()) {
				if((previousValue == (value.getValue() + 1)) || (previousValue == 1)) {
					toReturn.addAll(sorted.get(value));
					previousValue = value.getValue();
				} else if((toReturn.size() == 0) && (value.getAltValue() == 1)) {
					toReturn.addAll(sorted.get(value));
					previousValue = value.getAltValue();
				} else {
					toReturn = new ArrayList<Card>();
					toReturn.addAll(sorted.get(value));
					previousValue = value.getValue();
				}
			}
		}
		System.out.println(toReturn);
		return toReturn;
	}

	private List<Card> findFlush(List<Card> cards) {
		for(Entry<SUITE, List<Card>> suite : this.getSuiteCombinations(cards).entrySet()) {
			if(suite.getValue().size() > 4) {
				ArrayList<Card> toReturn = new ArrayList<Card>();
				TreeMap<VALUE, List<Card>> sorted = this.getValueCombinations(cards);
				for(VALUE value :sorted.descendingKeySet()) {
					toReturn.addAll(sorted.get(value));
					if(toReturn.size() == 5) {
						return toReturn;
					}
				}
			}
		}
		return new ArrayList<Card>();
	}

	private List<Card> findTwoPair(List<Card> cards) {
		List<Card> toReturn = new ArrayList<Card>();
		for(Entry<VALUE, List<Card>> item : this.getValueCombinations(cards).entrySet()) {
			if(item.getValue().size() == 2) {
				toReturn.addAll(item.getValue());
			}
			if(item.getValue().size() == 4) {
				toReturn.addAll(item.getValue());
			}
		}
		return toReturn;
	}

	private List<Card> findHouse(List<Card> cards) {
		List<Card> toReturn = new ArrayList<Card>();
		Set<Card> verifyWith = new HashSet<Card>();
		for(Entry<VALUE, List<Card>> item : this.getValueCombinations(cards).entrySet()) {
			if(item.getValue().size() == 3) {
				verifyWith.addAll(item.getValue());
			}
			if(item.getValue().size() == 2) {
				verifyWith.addAll(item.getValue());
			}
		}
		if(verifyWith.size() == 5) {
			toReturn.addAll(verifyWith);
		}
		return toReturn;
	}

	public List<Card> getInitialCards() {
		return this.cards;
	}

	protected List<Card> findHighestCard(List<Card> cards) {
		return cards;
	}

	protected List<Card> findHighestSet(List<Card> cards, int amount) {
		List<Card> toReturn = new ArrayList<Card>();
		int value = 0;
		for(Entry<VALUE, List<Card>> result : this.getValueCombinations(cards).entrySet()) {
			if(result.getValue().size() == amount) {
				if(toReturn.isEmpty()) {
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

	protected TreeMap<SUITE, List<Card>> getSuiteCombinations(List<Card> cards) {
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

	public Set<ResultingHand> getResults() {
		return results;
	}

	public ResultingHand getBestResult() {
		ResultingHand toReturn = new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD, new ArrayList<Card>());
		for(ResultingHand hand : results) {
			if(hand.getHand().ordinal() > toReturn.getHand().ordinal()) {
				toReturn = hand;
				if(hand.getValue() > toReturn.getValue()) {
					toReturn = hand;
				}
			}
		}
		return toReturn;
	}
}
