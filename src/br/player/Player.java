package br.player;

import br.Game;
import br.Global;
import br.Placeable;
import br.Vector;
import br.time.Clock;
import br.time.Timer;
import br.weapon.Weapon;

public class Player extends Placeable {
	
	private int distSleepHearWalking = 200;
	private int chanceSleepHearWalking = 30;
	
	private static final int speed = 50;
	private int sleepyness = 0;
	private Timer sleepTimer = new Timer();
	
	private PlayerStatus status = PlayerStatus.MOVING;
	private Game game;
	private final String name;
	private final Gender gender;
	private Weapon weapon;
	private double direction;
	private boolean dead = false;
	private Statistic statistic = new Statistic();
	
	public Gender getGender() {
		return gender;
	}
	
	public PlayerStatus getStatus() {
		return status;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public Player setWeapon(Weapon weapon) {
		this.weapon = weapon;
		return this;
	}
	
	private void randomDirection() {
		direction = Math.toRadians(Global.random.nextInt(360));
	}
	
	private void randomDirectionHalf() {
		direction = new Vector(0, 0).subtract(getPosition()).getAngle()
				+ Math.toRadians(Global.random.nextInt(181)-90);
	}
	
	public void updateCollisionStatus(Player pl) {
		if (this.entered(pl, distSleepHearWalking) 
				&& this.getStatus().equals(PlayerStatus.SLEEPING)
				&& Global.rollDice(chanceSleepHearWalking)) {
			wakeUp();
			Global.messenger.messageWakeNoise(this).messageEndOfLine();
		}
	}
	
	public void wakeUp() {
		status = PlayerStatus.MOVING;
		sleepTimer.setTicks(
				Global.randomDouble(0.8, 1.2)*(16*60-sleepyness));
	}
	
	public void step() {
		switch (status) {
		case SLEEPING:
			sleepyness -= 2;
			sleepTimer.decrease();
			if (sleepTimer.isZero()) {				
				Global.messenger.messageWake(this).messageEndOfLine();
				wakeUp();
			}
			break;
		case MOVING:
			sleepyness++;
			sleepTimer.decrease();
			if (sleepTimer.isZero()) {
				status = PlayerStatus.SLEEPING;
				Global.messenger.messageSleep(this).messageEndOfLine();
				sleepTimer.setTicks(
						Global.randomDouble(0.4, 0.6)*sleepyness);
			}
			if ((game.isCloseToBoundary(getPosition()) 
					&& (Math.abs(new Vector(0, 0).subtract(getPosition())
							.getAngle() - direction) > Math.toRadians(90)))) {
				randomDirectionHalf();
			}
			this.move(speed, direction);
			break;
		}
	}
	
	public boolean getDead() {
		return dead;
	}
	
	public void setDead(Clock time) {
		this.setPosition(null);
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
	
	public Player(Game game, String name, Gender gender, int x, int y) {
		this(game, name, gender, new Vector(x, y));
	}
	
	public Player(Game game, String name, Gender gender, Vector position) {
		super(position);
		this.game = game;
		this.name = name;
		this.gender = gender;
		randomDirection();
		this.sleepTimer.setTicks(
				Global.randomDouble(0.8, 1.2)*(16*60-sleepyness));
	}
}
