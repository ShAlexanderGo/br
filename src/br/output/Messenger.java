package br.output;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import br.Global;
import br.player.Player;
import br.time.Clock;
import br.weapon.Weapon;

public class Messenger {

	private MessageQueue queue = new MessageQueue();
	private boolean needToWait = false; 
	private boolean verbose = true;
	private boolean nonstop = false;
	private Scanner scanner;
	
	/*public Messenger customMessage(String message, boolean needToWait) {
		queue.addMessage(message);
		setNeedToWait(needToWait);
		return this;
	}*/
	
	public void flush() {
		if (!verbose)
			return;
		if (queue.isEmpty())
			return;
		System.out.print(Global.gameTime.toString() + " ");
		if (needToWait && !nonstop)
			queue.removeLast();
		while (!queue.isEmpty()) {
			String m = queue.removeFirst();
			System.out.print(m);
			if (m.equals(queue.getSep()) && !queue.isEmpty()) {
				System.out.print("           ");
			}
		}
		if (needToWait) {
			if (!nonstop)
				waitKey();
			needToWait = false;
		}
	}
	
	private String article(String noun) {
		switch (noun.charAt(0)) {
		case 'a':
			noun = "an " + noun;
			break;
		default:
			noun = "a " + noun;
			break;
		}
		return noun;
	}
	
	@SuppressWarnings("unused")
	private String modifyVerb(String verb, int size) {
		if (size == 1) {
			return verb + "s";
		} else {
			return verb;
		}
	}
	
	private void waitKey() {
		scanner.nextLine();
	}
	
	public void setNeedToWait(boolean needToWait) {
		this.needToWait = needToWait;
	}
	
	public void setNonstop(boolean nonstop) {
		this.nonstop = nonstop;
	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public Messenger() {
		scanner = new Scanner(System.in);
	}
	
	//messages
	
	public Messenger messageDayStarts(Clock clock) {
		RandomMessage mess = new RandomMessage();
		mess.add("New day starts.");
		mess.add("New day begins.");
		mess.add("Day " + Integer.toString(clock.getDays()) + " starts.");
		mess.add("Day " + Integer.toString(clock.getDays()) + " begins.");
		queue.addMessage(mess.get());
		return this;
	}
	
	public Messenger messageEndOfLine() {
		queue.addMessage(System.getProperty("line.separator"));
		queue.setIgnore(false);
		return this;
	}
	
	public Messenger messageFindsItem(Player pl, Weapon w) {
		String name = pl.getName();
		String weapon = article(w.getType().getName());
		RandomMessage mess = new RandomMessage();
		mess.add(name + " finds " + weapon + ".");
		mess.add(name + " picks up " + weapon + ".");
		queue.addMessage(mess.get());
		return this;
	}
	
	public Messenger messageGameRestricted() {
		RandomMessage mess = new RandomMessage();
		mess.add("The game is restricted to smaller area.");
		mess.add("The game is now restricted to smaller area.");
		queue.addMessage(mess.get());
		return this;
	}
	
	public Messenger messageGameStart(List<Player> players) {
		if (players.size() == 0) {
			queue.setIgnore(true);
			return this;
		}
		String names = "";
		if (players.size() == 1)
			names = players.get(0).getName();
		else {
			int i;
			for (i = 0; i < players.size() - 1; i++)
				names = names + players.get(i).getName() + " ";
			names = names + "and " + players.get(i).getName();
		}
		queue.addMessage("The game starts. The players are: " + names + ".");
		return this;
	}
	
	public Messenger messageKill(Player killer, Player killed) {
		RandomMessage mess = new RandomMessage();
		mess.add(killer.getName() + " kills " + killed.getName() + ".");
		if (killer.getWeapon() != null) {
			String weapon = killer.getWeapon().getType().getName();
			weapon = article(weapon);
			mess.add(killer.getName() + " uses " + weapon + " to kill " 
					+ killed.getName() + ".");
			mess.add(killer.getName() + " kills " + killed.getName() 
					+ " with " + weapon + ".");
			mess.add(killer.getName() + " kills " + killed.getName() 
					+ " using " + weapon + ".");		
		}
		queue.addMessage(mess.get());
		setNeedToWait(true);
		return this;
	}
	
	public Messenger messageNightStarts(Clock clock) {
		/*
		 * Night came on.
		 */
		RandomMessage mess = new RandomMessage();
		mess.add("Night falls.");
		mess.add("Night draws nigh.");
		queue.addMessage(mess.get());
		return this;
	}
	
	public Messenger messageNotToFight() {
		RandomMessage mess = new RandomMessage();
		mess.add("They decide not to fight.");
		mess.add("They avoid encounter.");
		queue.addMessage(mess.get());
		setNeedToWait(true);
		return this;
	}
	
	public Messenger messagePrintStatictics(List<Player> players) {
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
		queue.addMessage("Statistics:");
		this.messageEndOfLine();
		for (int i = 0; i < players.size(); i++) {
			Player pl = players.get(i);
			String line = pl.getName() + " " + pl.getStatistic().getKills() 
					+ " kills";
			if (i == players.size() - 1) {
				line = line + ".";
			} else {
				line = line + ";";
			}
			queue.addMessage(line);
			this.messageEndOfLine();
		}
		return this;
	}
	
	public Messenger messageReachedBoundary(Player player) {
		queue.addMessage(player.getName() + " reaches boundary and changes direction.");
		return this;
	}
	
	public Messenger messageRunInto(Player pl1, Player pl2) {
		RandomMessage mess = new RandomMessage();
		if (Global.rollDice(50)) {
			Player temp  = pl1;
			pl1 = pl2;
			pl2 = temp;
		}
		mess.add(pl1.getName() + " and " + pl2.getName() 
				+ " run into each other.");
		mess.add(pl1.getName() + " encounters " + pl2.getName() + ".");
		queue.addMessage(mess.get());
		return this;
	}
	
	public Messenger messageSleep(Player pl) {
		if (Global.rollDice(100))
			queue.setIgnore(true);
		queue.addMessage(pl.getName() + " goes to sleep.");
		return this;
	}
	
	public Messenger messageWake(Player pl) {
		if (Global.rollDice(100))
			queue.setIgnore(true);
		queue.addMessage(pl.getName() + " wakes up.");
		return this;
	}
	
	public Messenger messageWinsGame(Player player) {
		queue.addMessage("Game ends. " + player.getName() + " wins the game.");
		return this;
	}
	
}