package br.player;

import br.Global;
import br.weapon.Weapon;
import br.weapon.WeaponType;

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
			int bonus = weapon == null ? 0 : weapon.getAttackBonus();
			attack = Global.random.nextInt(WeaponType.basicAttack + bonus);
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
