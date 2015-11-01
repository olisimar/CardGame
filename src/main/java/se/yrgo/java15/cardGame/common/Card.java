package se.yrgo.java15.cardGame.common;

import se.yrgo.java15.cardGame.enums.SUITE;
import se.yrgo.java15.cardGame.enums.VALUE;

public class Card implements Comparable {

	private final SUITE suite;
	private final VALUE value;
	
	public Card(SUITE suite, VALUE value) {
		this.suite = suite;
		this.value = value;
	}

	public SUITE getSuite() {
		return suite;
	}

	public VALUE getValue() {
		return value;
	}
	
	public String toString() {
		return this.value +" of "+ this.suite;
	}
	
	public int hashCode() {
		return ((suite.ordinal() * 100) + value.ordinal());
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Card) {
			return (this.hashCode() == obj.hashCode());
		}
		return false;
	}

	public int compareTo(Object o) {
		if(o instanceof Card) {
			if(o.hashCode() < this.hashCode()) {
				return -1;
			} else if(o.hashCode() > this.hashCode()) {
				return 1;
			} else {
				return 0;
			}
		}
		return -1;
	}
}
