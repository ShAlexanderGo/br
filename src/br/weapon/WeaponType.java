package br.weapon;

public enum WeaponType {
	KNIFE("knife", 1);
	
	private String name;
	private int attackBonus;
	
	public String getName() {
		return name;
	}
	
	public int getAttackBonus() {
		return attackBonus;
	}
	
	WeaponType(String name, int attackBonus) {
		this.name = name;
		this.attackBonus = attackBonus;
	}
	
}
