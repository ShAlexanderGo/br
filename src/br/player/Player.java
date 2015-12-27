package br.player;

import java.util.ArrayList;

import br.Game;
import br.Global;
import br.Vector;
import br.time.Clock;

public class Player {
	
	private final int speed = 50;
	
	private Game game;
	private String name;
	private Vector position;
	private double direction;
	private boolean dead = false;
	private ArrayList<Player> vicinity = new ArrayList<Player>();
	private Statistic statistic = new Statistic();
	
	/**
	 * if player is this, player won't be added.
	 */
	public void addVicinity(Player player) {
		if (player.equals(this))
			return;
		vicinity.add(player);
	}
	
	public void clearVicinity() {
		vicinity.clear();
	}
	
	public double distanceTo(Player player) {
		return this.position.distanceTo(player.getPosition());
	}
	
	/*
	 * Player is not in the vicinity of itself
	 */
	public boolean isInVicinity(Player player) {
		for (int i = 0; i < vicinity.size(); i++) {
			if (vicinity.get(i).equals(player))
				return true;
		}
		return false;
	}
	
	private void randomDirection() {
		direction = Math.toRadians(Global.random.nextInt(360));
	}
	
	private void randomDirectionHalf() {
		direction = new Vector(0, 0).subtract(position).getAngle()
				+ Math.toRadians(Global.random.nextInt(181)-90);
	}
	
	public void step() {
		if (game.isCloseToBoundary(position)) {
			
			if (Math.abs(new Vector(0, 0).subtract(position).getAngle() - direction) 
					> Math.toRadians(90)) {
				//Global.messenger.messageReachedBoundary(this).messageEndOfLine();
				randomDirectionHalf();
			}
		}
		position.addByAngle(speed, direction);
	}
	
	public boolean getDead() {
		return dead;
	}
	
	public void setDead(Clock time) {
		this.dead = true;
		this.statistic.setTimeOfDeath(new Clock(time));
	}
	
	public Player setDirection(double direction) {
		this.direction = direction;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Statistic getStatistic() {
		return statistic;
	}
	
	public Player(Game game, String name, int x, int y) {
		this(game, name, new Vector(x, y));
	}
	
	public Player(Game game, String name, Vector position) {
		this.game = game;
		this.name = name;
		this.position = position;
		randomDirection();
	}
}
