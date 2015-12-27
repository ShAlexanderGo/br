package br.player;

import br.time.Clock;

public class Statistic {

	private int kills;
	private Clock timeOfDeath = new Clock();
	
	public void increaseKills() {
		kills++;
	}
	
	public int getKills() {
		return kills;
	}
	
	public Clock getTimeOfDeath() {
		return timeOfDeath;
	}
	
	public void setTimeOfDeath(Clock clock) {
		timeOfDeath = clock;
	}
	
}
