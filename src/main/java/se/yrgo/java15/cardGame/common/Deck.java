package se.yrgo.java15.cardGame.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import se.yrgo.java15.cardGame.exceptions.OutOfCardsException;

public class Deck {

	private TreeSet<Card> cards;
	private List<Card> currentOrder = new ArrayList<Card>();
	private List<Card> dealt = new ArrayList<Card>();

	public Deck(Set<Card> cards) {
		this.cards = new TreeSet<Card>(cards);
		this.currentOrder = new ArrayList<Card>(cards);
	}
	
	public void shuffle() {
		Collections.shuffle(this.currentOrder);
	}
	
	
	public Card getTopCard() {
		if(this.hasOneMoreCard()) {
			Card card = this.currentOrder.get(0);
			this.dealt.add(card);
			this.currentOrder.remove(0);
			return card;
		}
		throw new OutOfCardsException("Deck is empty of cards, none more to give.");
	}
	
	public List<Card> getCards(int amount) {
		List<Card> toReturn = new ArrayList<Card>();
		while(amount > 0) {
			toReturn.add(this.getTopCard());
			amount--;
		}
		return toReturn;
	}
	
	public Set<Card> getAllCards() {
		return this.cards;
	}

	public boolean hasOneMoreCard() {
		if(this.currentOrder.size() == 0) {
			return false;
		}
		return true;
	}

	public List<Card> getDealtCards() {
		return this.dealt;
	}
}
