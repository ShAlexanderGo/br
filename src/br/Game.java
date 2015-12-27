package br;

import java.util.ArrayList;

import br.player.Player;
import br.player.PlayerBuilder;
import br.time.Timer;

public class Game {
	private int radius = 10_000;
	
	private Group playersAll;
	private Group players = new Group();
	private Timer lastKill = new Timer(1, 0, 0);
	private EncounterFinder finder = new EncounterFinder();
	
	public boolean isCloseToBoundary(Vector position) {
		if (position.getLength() > radius*0.9)
			return true;
		else
			return false;
	}
	
	public String routine() {
		Group gameStart = new Group();
		for (int i = 0; i < players.size(); i++) {
			gameStart.add(players.get(i));
		}
		Global.messenger.messageGameStart(gameStart).messageEndOfLine();
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
    	players = new PlayerBuilder(this).get();
    	playersAll = new Group(players);
    }
}
