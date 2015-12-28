package br.player;

import java.util.LinkedList;
import java.util.List;

import br.Game;
import br.Global;
import br.Placeable;
import br.Vector;
import br.time.Clock;

public class Player extends Placeable {
	
	private static final int speed = 50;
	
	private Game game;
	private String name;
	
	private double direction;
	private boolean dead = false;
	private List<Placeable> vicinity = new LinkedList<Placeable>();
	private Statistic statistic = new Statistic();
	
	public void updateVicinity(List<? extends Placeable> objects) {
		vicinity.clear();
		for (Placeable object : objects) {
			if (this.distanceTo(object) < 100)
				vicinity.add(object);
		}
	}
	
	public boolean isInVicinity(Placeable object) {
		return vicinity.contains(object);
	}
	
	private void randomDirection() {
		direction = Math.toRadians(Global.random.nextInt(360));
	}
	
	private void randomDirectionHalf() {
		direction = new Vector(0, 0).subtract(getPosition()).getAngle()
				+ Math.toRadians(Global.random.nextInt(181)-90);
	}
	
	public void step() { 
		if (game.isCloseToBoundary(getPosition())) {
			
			if (Math.abs(new Vector(0, 0).subtract(getPosition()).getAngle() 
					- direction) > Math.toRadians(90)) {
				//Global.messenger.messageReachedBoundary(this).messageEndOfLine();
				randomDirectionHalf();
			}
		}
		getPosition().addByAngle(speed, direction);
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
	
	public Player setDirection(int x, int y) {
		setDirection(new Vector(x, y).subtract(getPosition()).getAngle());
		return this;
	}
	
	public String getName() {
		return name;
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
		this.setPosition(position);
		randomDirection();
	}
}
