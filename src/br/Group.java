package br;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Group {
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public void add(Group group) {
		for (int i = 0; i < group.size(); i++) {
			this.add(group.get(i));
		}
	}
	
	public void add(Player player) {
		players.add(player);
	}
	
	public double distanceTo(Group group) {
		double minimum = Double.MAX_VALUE;
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < group.size(); j++) {
				double distance = this.get(i).distanceTo(group.get(j));
			    minimum = Math.min(distance, minimum);
			}
		}
		return minimum;
	}
	
	public Player get(int i) {
		return players.get(i);
	}
	
	public String getNames() {
		if (players.size() == 0)
			return null;
		if (players.size() == 1)
			return players.get(0).getName();
		Collections.shuffle(players);
		String names = players.get(0).getName() 
				+ " and " 
				+ players.get(1).getName();
		for (int i = 2; i < players.size(); i++) {
			names = players.get(i).getName() + ", " + names;
		}
		return names;
	}
	
	/**
	 * Checks if group is in vicinity of this.
	 */
	public boolean isInVicinity(Group group) {
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < group.size(); j++) {
				if (!this.get(i).isInVicinity(group.get(j)))
					return false;
			}
		}
		return true;
	}
	
	public void remove(int i) {
		players.remove(i);
	}
	
	public void resolveFight() {
		if (players.size() <= 1)
			return;
		Global.messenger.messageTimeStamp();
		ArrayList<TacticPlayer> tacticals = new ArrayList<TacticPlayer>();
		for (Player player : players)
			tacticals.add(TacticPlayer.generate(player));
		int maximum = Integer.MIN_VALUE;
		for (TacticPlayer tactical : tacticals)
			maximum = Math.max(maximum, tactical.getAttack());
		Group winners = new Group();
		Group losers = new Group();
		for (TacticPlayer tactical : tacticals)
			if (tactical.getAttack() < maximum)
				losers.add(tactical.getPlayer());
			else 
				winners.add(tactical.getPlayer());
		for (int i = 0; i < losers.size(); i++) {
			losers.get(i).setDead(Global.gameTime);
			winners.get(Global.random.nextInt(winners.size())).getStatistic()
					.increaseKills();
		}
		if (losers.size() != 0) {
			Global.messenger.messangeRunInto(this)
					.messageKill(winners, losers)
					.messageEndOfLine();
		} else {
			Global.messenger.messangeRunInto(this)
					.messageNotToFight()
					.messageEndOfLine();
		}
	}
	
	public int size() {
		return players.size();
	}
	
	public void sortByTimeOfDeath() {
		players.sort(new Comparator<Player>() {
			@Override
			public int compare(Player arg0, Player arg1) {
				if (arg0.getDead()) {
					if (arg1.getDead()) {
						return arg0.getStatistic().getTimeOfDeath().getTicks()
								- arg1.getStatistic().getTimeOfDeath().getTicks();
					} else {
						return -1;
					}
				} else {
					if (arg1.getDead()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		});
	}
	
	public Group() {}
	
	public Group(Player player) {
		players.add(player);
	}
	
	public Group(Group group) {
		for (int i = 0; i < group.size(); i++) {
			players.add(group.get(i));
		}
	}
	
}
