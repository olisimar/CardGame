package se.yrgo.java15.cardGame.common;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Card> cards;
	
	public Player(String name) {
		if(name.length() > 15) {
			name = name.substring(0, 15);
		}
		this.name = name;
	}
	
	public int acceptCard(Card card) {
		if(this.cards == null) {
			this.cards = new ArrayList<Card>();
		}
		this.cards.add(card);
		return this.cards.size();
	}
	
	public List<Card> getCards() {
		if(this.cards == null) {
			this.cards = new ArrayList<Card>();
		}
		return this.cards;
	}
	
	public String getName() {
		return this.name;
	}
}
