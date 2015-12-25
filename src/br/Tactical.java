package br;

public class Tactical {

	private Player player;
	private Integer attack;
	
	public static Tactical generate(Player player) {
		return new Tactical(player);
	}
	
	public int getAttack() {
		if (attack == null) {
			attack = Global.random.nextInt(2);
		}
		return attack;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	private Tactical(Player player) {
		this.player = player;
	}
	
}
