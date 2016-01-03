package br;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	private List<Player> playersAll;
	private List<Player> players = new ArrayList<Player>();
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
			Global.gameTime.step();
			lastKill.step();
			players.forEach(pl -> pl.step());
			finder.resolveCollisions(players);
			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);
				if (player.getDead()) {
					lastKill.reset();
					players.remove(i);
					i--;
				}
			}
			finder.updateCollisionStatus(players);
			finder.resolveCollisions(players, weapons);
			ListIterator<Weapon> it = weapons.listIterator();
			while (it.hasNext()) {
				Weapon w = it.next();
				if ((w.getPosition() == null) 
						|| (w.distanceTo(0, 0) > radius)) {
					it.remove();
				}
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
				return players.get(0).getName();
			}
		}
	}
	
    public Game() {
    	Global.initialize();
    	players = new PlayerBuilder(this, 10).get();
    	playersAll = new ArrayList<Player>(players);
    	for (int i = 0; i < 20; i++) {
    		int length = Global.random.nextInt(radius);
    		double angle = Global.random.nextDouble() * Math.toRadians(360);
    		WeaponType weapon = Global.randomElement(Arrays.asList(WeaponType.values()));
    		weapons.add(new Weapon(weapon, 
    				new Vector().addByAngle(length, angle)));
    	}
    }
}
