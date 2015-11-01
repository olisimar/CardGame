package se.yrgo.java15.cardGame.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Table {

	private List<Player> players;
	private Player dealer;

	public Table(Set<Player> players) {
		this.players = new ArrayList<Player>(players);
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public Player getDealer() {
		return dealer;
	}

	public boolean setDealer(Player dealer) {
		if(this.players.contains(dealer)) {
			this.dealer = dealer;
			return true;
		}
		return false;
	}
}
