package br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.player.Player;
import br.player.TacticPlayer;


public class EncounterFinder {

	private final int encounterDistance = 100;
	
	public ArrayList<Group> find(Group players) {
		ArrayList<Group> groups = new ArrayList<Group>();
		for (Player pl : players)
			groups.add(new Group(pl));
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
	

	public void resolveFight(Group group) {
		if (group.size() <= 1)
			return;
		ArrayList<TacticPlayer> tacticals = new ArrayList<TacticPlayer>();
		for (Player player : group)
			tacticals.add(TacticPlayer.generate(player));
		int maximum = Integer.MIN_VALUE;
		for (TacticPlayer tactical : tacticals) {
			int attack = tactical.getAttack();
			if (attack > maximum)
				maximum = attack;
		}
		Group winners = new Group();
		Group losers = new Group();
		for (TacticPlayer tactical : tacticals)
			if (tactical.getAttack() < maximum)
				losers.add(tactical.getPlayer());
			else 
				winners.add(tactical.getPlayer());
		Global.messenger.messageRunInto(group);
		if (losers.size() == 0) {
			Global.messenger.messageNotToFight()
			.messageEndOfLine();
			return;
		}
		Map<Player, Group> map = new HashMap<Player, Group>();
		for (Player loser : losers) {
			loser.setDead(Global.gameTime);
			Player winner = Global.randomElement(winners);
			winner.getStatistic().increaseKills();
			if (map.containsKey(winner)) {
				map.get(winner).add(loser);
			} else {
				map.put(winner, new Group(loser));
			}
		}
		for (Map.Entry<Player, Group> entry : map.entrySet())
			Global.messenger
					.messageKill(new Group(entry.getKey()), entry.getValue());
		Global.messenger.messageEndOfLine();
	}
	
	public EncounterFinder() {}
	
}
