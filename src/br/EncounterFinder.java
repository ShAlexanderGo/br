package br;

import java.util.ArrayList;


public class EncounterFinder {

	private final int encounterDistance = 100;
	
	public ArrayList<Group> find(Group players) {
		ArrayList<Group> groups = new ArrayList<Group>();
		for (int i = 0; i < players.size(); i++)
			groups.add(new Group(players.get(i)));
		for (int i = 0; i < groups.size(); i++) {
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
		return groups;
	}
	
	public EncounterFinder() {}
	
}
