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
		List<Card> highestCards = findHighestCards(cards);
		if(highestCards.size() <= 5) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.HIGH_CARD, highestCards));
		}

		List<Card> onePair = this.findHighestSet(cards, 2);
		if(!onePair.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.ONE_PAIR, onePair));
		}

		List<Card> twoPair = this.findTwoPair(cards);
		if(twoPair.size() == 4) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.TWO_PAIR, twoPair));
		} else {
			System.out.println(twoPair);
		}

		List<Card> tripple = this.findHighestSet(cards, 3);
		if(!tripple.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.THREE_OF_A_KIND, tripple));
		}

		List<Card> flush = this.findFlush(cards);
		if(flush.size() >= 5) {
			ArrayList<Card> tmp = new ArrayList<Card>();
			for(int i = 0; i < 5; i++) {
				tmp.add(flush.get(i));
			}
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.FLUSH, tmp));
		}

		List<Card> straight = this.findStraight(cards);
		if(straight.size() >= 5) {
			ArrayList<Card> tmp = new ArrayList<Card>();
			for(Card card : straight) {
				tmp.add(card);
				if(tmp.size() == 5) {
					break;
				}
			}
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT, tmp));
		}

		List<Card> house = this.findHouse(cards);
		if(!house.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.FULL_HOUSE, house));
		}

		List<Card> quattro = this.findHighestSet(cards, 4);
		if(!quattro.isEmpty()) {
			this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.FOUR_OF_A_KIND, quattro));
		}

		if(flush.size() >= 5) {
			List<Card> fixes = this.findStraight(flush);
			if(this.findStraight(flush).size() >= 5) {
				int combValue = 0;
				ArrayList<Card> result = new ArrayList<Card>();
				for(Card card : flush) {
					combValue += card.getValue().getValue();
					result.add(card);
					if(result.size() == 5) {
						break;
					}
				}
				if(combValue >= 60) {
					this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.ROYAL_FLUSH, result));
				} else {
					this.results.add(new ResultingHand(TEXAS_HOLDEM_RANKED_RESULT.STRAIGHT_FLUSH, result));
				}
			}
		}
	}

	private List<Card> findHighestCards(List<Card> cards) {
		List<Card> highest = new ArrayList<Card>();
		TreeMap<VALUE, List<Card>> intermediate = this.getValueCombinations(cards);
		for(Entry<VALUE,List<Card>> item : intermediate.descendingMap().entrySet()) {
			for(Card card : item.getValue()) {
				highest.add(card);
				if(highest.size() == 5) {
					System.out.println(highest);
					return highest;
				}
			}
		}
		return highest;
	}

	private List<Card> findStraight(List<Card> cards) {
		ArrayList<Card> toReturn = new ArrayList<Card>();
		TreeMap<VALUE, List<Card>> sorted = this.getValueCombinations(cards);
		Card ace = null;
		if(sorted.size() >= 5) {
			int previousValue = 0;
			for(VALUE value : sorted.descendingKeySet()) {
				if((previousValue == (value.getValue() + 1)) || (previousValue == 1)) {
					toReturn.add(sorted.get(value).get(0));
					previousValue = value.getValue();
				} else if((toReturn.size() == 0) && (value.getAltValue() == 1)) {
					ace = sorted.get(value).get(0);
					toReturn.add(sorted.get(value).get(0));
					previousValue = value.getAltValue();
				} else {
					toReturn = new ArrayList<Card>();
					toReturn.addAll(sorted.get(value));
					previousValue = value.getValue();
				}
			}
		}
		if((toReturn.size() == 4) && (ace != null)) {
			if(toReturn.get(3).getValue() == VALUE.TWO) {
				toReturn.add(ace);
			}
		}
		return toReturn;
	}

	private List<Card> findFlush(List<Card> cards) {
		for(Entry<SUITE, List<Card>> suite : this.getSuiteCombinations(cards).entrySet()) {
			if(suite.getValue().size() > 4) {
				TreeMap<VALUE, List<Card>> sorted = this.getValueCombinations(cards);
				ArrayList<Card> toReturn = new ArrayList<Card>();
				for(VALUE value : sorted.descendingKeySet()) {
					toReturn.addAll(sorted.get(value));
				}
				return toReturn;
			}
		}
		return new ArrayList<Card>();
	}

	private List<Card> findTwoPair(List<Card> cards) {
		List<Card> toReturn = this.findHighestSet(cards, 2);
		if(toReturn.size() == 2) {
			cards.remove(toReturn.get(0));
			cards.remove(toReturn.get(1));
			toReturn.addAll(this.findHighestSet(cards, 2));
			cards.add(toReturn.get(1));
			cards.add(toReturn.get(0));
			System.out.println(toReturn);
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
		if(verifyWith.size() == 6) {

		}
		if(verifyWith.size() == 7) {

		}
		return toReturn;
	}

	public List<Card> getInitialCards() {
		return this.cards;
	}

	protected List<Card> findHighestSet(List<Card> cards, int amount) {
		List<Card> toReturn = new ArrayList<Card>();
		TreeMap<VALUE, List<Card>> result = this.getValueCombinations(cards);
		for(VALUE key : result.descendingKeySet()) {
			if(result.get(key).size() >= amount) {
				for(Card card : result.get(key)) {
					toReturn.add(card);
					if(toReturn.size() == amount) {
						return toReturn;
					}
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
			if(hand.getHand().ordinal() >= toReturn.getHand().ordinal()) {
				System.out.println(hand.getHand() +" beats "+ toReturn.getHand());
				if((hand.getHand().ordinal() == toReturn.getHand().ordinal()) && (hand.getValue() < toReturn.getValue())) {
					toReturn = hand;
				} else {
					toReturn = hand;
				}
			}
		}
		return toReturn;
	}
}
