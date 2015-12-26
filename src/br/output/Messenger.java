package br.output;

import java.util.Scanner;

import br.Group;
import br.Player;
import br.time.Clock;

public class Messenger {

	private MessageQueue queue = new MessageQueue();
	private boolean timeStamped = false;
	private boolean needToWait = false; 
	private Scanner scanner;
	private String lineSeparator = System.getProperty("line.separator");
	
	public Messenger customMessage(String message, boolean needToWait) {
		queue.addMessage(message);
		setNeedToWait(needToWait);
		return this;
	}
	
	public void flush() {
		if (queue.isEmpty())
			return;
		if (needToWait)
			queue.removeLast();
		while (!queue.isEmpty()) {
			System.out.print(queue.removeFirst());
		}
		if (needToWait) {
			waitKey();
			needToWait = false;
		}
		timeStamped = false;
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
		queue.addMessage(lineSeparator);
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
		queue.addMessage(killer.getNames() 
				+ " " 
				+ modifyVerb("kill", killer.size())
				+ " "
				+ killed.getNames()
				+ ".");
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
	
	public Messenger messageReachedBoundary(Player player) {
		queue.addMessage(player.getName() + " reaches boundary and changes direction.");
		return this;
	}
	
	public Messenger messangeRunInto(Group group) {
		queue.addMessage(group.getNames() + " run into each other.");
		return this;
	}
	
	public Messenger messageTimeStamp(Clock clock) {
		if (timeStamped)
			return this;
		timeStamped = true;
		this.customMessage(clock.toString(), false).messageEndOfLine();
		return this;
	}
	
	public Messenger messageWinsGame(Player player) {
		queue.addMessage("Game ends. " + player.getName() + " wins the game.");
		return this;
	}
	
	public Messenger() {
		scanner = new Scanner(System.in);
	}
	
}
