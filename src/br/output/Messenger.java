package br.output;

import java.util.Scanner;

import br.Global;
import br.Group;
import br.player.Player;
import br.time.Clock;
import br.weapon.Weapon;

public class Messenger {

	private MessageQueue queue = new MessageQueue();
	private boolean needToWait = false; 
	private boolean verbose = true;
	private boolean nonstop = false;
	private Scanner scanner;
	
	public Messenger customMessage(String message, boolean needToWait) {
		queue.addMessage(message);
		setNeedToWait(needToWait);
		return this;
	}
	
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
	
	//TODO
	private String article(String noun) {
		return "a " + noun;
	}
	
	private String modifyVerb(String verb, int size) {
		if (size == 1) {
			return verb + "s";
		} else {
			return verb;
		}
	}
	
	public void waitKey() {
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
		return this;
	}
	
	public Messenger messageFindsItem(Player pl, Weapon w) {
		queue.addMessage(pl.getName() + " finds " 
				+ article(w.getType().getName()) + ".");
		return this;
	}
	
	public Messenger messageGameRestricted() {
		queue.addMessage("Game is restricted to smaller area.");
		return this;
	}
	
	public Messenger messageGameStart(Group group) {
		queue.addMessage("The game starts. The players are: " + group.getNames() + ".");
		return this;
	}
	
	public Messenger messageKill(Group killer, Group killed) {
		if (killed.isEmpty())
			return this;
		RandomMessage mess = new RandomMessage();
		mess.add(killer.getNames() 
				+ " " 
				+ modifyVerb("kill", killer.size())
				+ " "
				+ killed.getNames()
				+ ".");
		if ((killer.size() == 1) && (killer.get(0).getWeapon() != null)) {
			String weapon = killer.get(0).getWeapon().getType().getName();
			weapon = article(weapon);
			mess.add(killer.getNames() + " uses " + weapon + " to kill " 
					+ killed.getNames() + ".");
			mess.add(killer.getNames() + " kills " + killed.getNames() 
					+ " with " + weapon + ".");
			mess.add(killer.getNames() + " kills " + killed.getNames() 
					+ " using " + weapon + ".");
			
		}
		queue.addMessage(mess.get());
		setNeedToWait(true);
		return this;
	}
	
	public Messenger messageNightStarts(Clock clock) {
		queue.addMessage("Night falls.");
		return this;
	}
	
	public Messenger messageNotToFight() {
		queue.addMessage("They decide not to fight.");
		setNeedToWait(true);
		return this;
	}
	
	public Messenger messagePrintStatictics(Group group) {
		group.sortByTimeOfDeath();
		queue.addMessage("Statistics:");
		this.messageEndOfLine();
		for (int i = 0; i < group.size(); i++) {
			Player pl = group.get(i);
			String line = pl.getName() + " " + pl.getStatistic().getKills() 
					+ " kills";
			if (i == group.size() - 1) {
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
	
	public Messenger messageRunInto(Group group) {
		queue.addMessage(group.getNames() + " run into each other.");
		return this;
	}
	
	public Messenger messageWinsGame(Player player) {
		queue.addMessage("Game ends. " + player.getName() + " wins the game.");
		return this;
	}
	
}