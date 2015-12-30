package br;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import br.player.Player;
import br.player.PlayerBuilder;
import br.time.Timer;
import br.weapon.Weapon;
import br.weapon.WeaponType;

public class Game {
	private int radius = 10_000;
	
	private Group playersAll;
	private Group players = new Group();
	private List<Weapon> weapons = new LinkedList<Weapon>();
	private Timer lastKill = new Timer(1, 0, 0);
	private EncounterFinder finder = new EncounterFinder();
	
	public boolean isCloseToBoundary(Vector position) {
		if (position.getLength() > radius*0.9)
			return true;
		else
			return false;
	}
	
	public String routine() {
		Global.messenger.messageGameStart(playersAll).messageEndOfLine();
		lastKill.increase(); //для ровного счета
		while (true) {
			Global.gameTime.increase();
			lastKill.decrease();
			if ((Global.gameTime.getHours() == 0)
					&& (Global.gameTime.getMinutes() == 0))
				Global.messenger.setNeedToWait(true);
			if (Global.gameTime.getMinutes() == 0) {
				if (Global.gameTime.getHours() == 6) {
					Global.messenger.messageDayStarts(Global.gameTime)
							.messageEndOfLine();
				} else if (Global.gameTime.getHours() == 23) {
					Global.messenger.messageNightStarts(Global.gameTime)
							.messageEndOfLine();
				}
			}
			for (int i = 0; i < players.size(); i++) {
				players.get(i).step();
			}
			ArrayList<Group> groups = finder.find(players);
			for (int i = 0; i < groups.size(); i++) {
				groups.get(i).resolveFight();
			}
			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);
				if (player.getDead()) {
					lastKill.reset();
					players.remove(i);
					i--;
				}
			}
			for (Weapon weapon : weapons) {
				Group group = new Group();
				for (Player pl : players) {
					if (pl.distanceTo(weapon) < 50)
						group.add(pl);
				}
				if (group.isEmpty())
					continue;
				int i = Global.random.nextInt(group.size());
				Player pl = group.get(i);
				weapon.setPosition(null);
				pl.setWeapon(weapon);
				Global.messenger.messageFindsItem(pl, weapon)
						.messageEndOfLine();
			}
			ListIterator<Weapon> it = weapons.listIterator();
			while (it.hasNext()) {
				if (it.next().getPosition() == null)
					it.remove();
			}
			for (int i = 0; i < players.size(); i++) {
				players.get(i).updateVicinity(players);
			}
			if (lastKill.isZero()) {
				radius = (int)(0.9 * radius);
				Global.messenger.messageGameRestricted().messageEndOfLine();
				lastKill.reset();;
			}
			Global.messenger.flush();
			if (players.size() == 1) {
				Global.messenger.messageWinsGame(players.get(0))
						.messageEndOfLine();
				Global.messenger.messagePrintStatictics(playersAll);
				Global.messenger.flush();
				return players.getNames();
			}
		}
	}
	
    public Game() {
    	Global.initialize();
    	players = new PlayerBuilder(this, 10).get();
    	playersAll = new Group(players);
    	for (int i = 0; i < 10; i++) {
    		int length = Global.random.nextInt(radius);
    		double angle = Global.random.nextDouble() * Math.toRadians(360);
    		weapons.add(new Weapon(WeaponType.KNIFE, 
    				new Vector().addByAngle(length, angle)));
    	}
    }
}
