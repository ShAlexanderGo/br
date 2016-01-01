package br.player;

import br.Global;
import br.weapon.Weapon;
import br.weapon.WeaponType;

public class TacticPlayer {

	private Player player;
	private Integer attack;
	private Weapon weapon;
	private double multiplier;
	
	public static TacticPlayer generate(Player pl) {
		double multiplier = 1.0;
		if (pl.getStatus().equals(PlayerStatus.SLEEPING))
			multiplier = 0.5;
		return new TacticPlayer(pl)
				.setWeapon(pl.getWeapon())
				.setMultiplier(multiplier);
	}
	
	public TacticPlayer setWeapon(Weapon weapon) {
		this.weapon = weapon;
		return this;
	}
	
	public TacticPlayer setMultiplier(double multiplier) {
		this.multiplier = multiplier;
		return this;
	}
	
	public int getAttack() {
		if (attack == null) {
			int bonus = weapon == null ? 0 : weapon.getAttackBonus();
			int maxAttack = (int)((WeaponType.basicAttack + bonus) 
					* multiplier);
			attack = Global.random.nextInt(maxAttack);
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
