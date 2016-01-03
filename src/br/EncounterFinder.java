package br;

import java.util.Collections;
import java.util.List;
import br.player.Player;
import br.player.PlayerStatus;
import br.player.TacticPlayer;
import br.weapon.Weapon;


public class EncounterFinder {

	private final int fightDistance = 100;
	
	public void resolveCollisions(List<Player> players) {
		Collections.shuffle(players);
		for (int i = 0; i < players.size(); i++) {
			Player pl1 = players.get(i);
			for (int j = i + 1; j < players.size(); j++) {
				Player pl2 = players.get(j);
				if (pl1.entered(pl2, fightDistance)) {
					resolveFight(pl1, pl2);
				}
			}
		}
	}
	
	public void updateCollisionStatus(List<Player> players) {
		Collections.shuffle(players);
		for (Player pl1 : players) {
			for (Player pl2 : players) {
				pl1.updateCollisionStatus(pl2);
			}
		}
	}
	
	public void resolveCollisions(List<Player> players, List<Weapon> weapons) {
		Collections.shuffle(players);
		for (Weapon w : weapons) {
			for (Player pl : players) {
				if (pl.entered(w, 50)) {
					w.setPosition(null);
					Weapon pw = pl.getWeapon();
					if ((pw == null) 
							|| (pw.getAttackBonus() < w.getAttackBonus())) {
						pl.setWeapon(w);
					}
					Global.messenger.messageFindsItem(pl, w)
							.messageEndOfLine();
				}
			}
		}
	}

	public void resolveFight(Player pl1, Player pl2) {
		TacticPlayer tpl1 = TacticPlayer.generate(pl1);
		TacticPlayer tpl2 = TacticPlayer.generate(pl2);
		if (tpl1.getAttack() == tpl2.getAttack()) {
			Global.messenger.messageNotToFight(pl1, pl2);
		} else {
			Player winner;
			Player loser;
			if (tpl1.getAttack() > tpl2.getAttack()) {
				winner = pl1;
				loser = pl2;
			} else {
				winner = pl2;
				loser = pl1;
			}
			if (loser.getStatus().equals(PlayerStatus.SLEEPING)) {
				Global.messenger.messageKillSleeper(winner, loser);
			} else {
				Global.messenger.messageKill(winner, loser);
			}
			loser.setDead(Global.gameTime);
			winner.getStatistic().increaseKills();
		}
		Global.messenger.messageEndOfLine();
	}
	
	public EncounterFinder() {}
	
}
