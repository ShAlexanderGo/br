package br.player;

import br.Global;
import br.weapon.Weapon;

public class TacticPlayer {

	private Player player;
	private Integer attack;
	private Weapon weapon;
	
	public static TacticPlayer generate(Player pl) {
		return new TacticPlayer(pl).setWeapon(pl.getWeapon());
	}
	
	public TacticPlayer setWeapon(Weapon weapon) {
		this.weapon = weapon;
		return this;
	}
	
	public int getAttack() {
		if (attack == null) {
			int bonus = weapon == null ? 0 : weapon.getType().getAttackBonus();
			attack = Global.random.nextInt(2 + bonus);
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
