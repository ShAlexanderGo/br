package br;

import java.util.ArrayList;

public class EncounterFinder {

	private final int encounterDistance = 100;
	
	public ArrayList<Group> find(Group players) {
		ArrayList<Group> groups = new ArrayList<Group>();
		for (int i = 0; i < players.size(); i++)
			groups.add(new Group(players.get(i)));
		for (int i = 0; i < groups.size(); i++) {
			//System.out.println(i + " of " + groups.size());
			Group group1 = groups.get(i);
			for (int j = i + 1; j < groups.size(); j++) {
				Group group2 = groups.get(j);
				if ((group1.distanceTo(group2) < encounterDistance)
						&& (!group1.isInVicinity(group2))) {
					group1.add(group2);
					groups.remove(j);
					j = i;
				}
			}
		}
		updateVicinities(players);
		return groups;
	}
	
	public void updateVicinities(Group players) {
		for (int i = 0; i < players.size(); i++) {
			Player player1 = players.get(i);
			player1.clearVicinity();
			for (int j = 0; j < players.size(); j++) {
				Player player2 = players.get(j);
				if (player1.distanceTo(player2) < encounterDistance)
					player1.addVicinity(player2);
			}
		}
	}
	
	public EncounterFinder() {}
	
}
