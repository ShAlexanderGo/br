package br.weapon;

public enum WeaponType {
	//NOTHING("bare hands", 0, false),
	KNIFE("knife", 1, true);
	
	private String name;
	private int attackBonus;
	private boolean droppable;
	
	public String getName() {
		return name;
	}
	
	public int getAttackBonus() {
		return attackBonus;
	}
	
	public boolean isDroppable() {
		return droppable;
	}
	
	WeaponType(String name, int attackBonus, boolean droppable) {
		this.name = name;
		this.attackBonus = attackBonus;
	}
	
}
