package br;

public class TacticPlayer {

	private Player player;
	private Integer attack;
	
	public static TacticPlayer generate(Player player) {
		return new TacticPlayer(player);
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
	
	private TacticPlayer(Player player) {
		this.player = player;
	}
	
}
