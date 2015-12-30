package br.weapon;

import java.util.ArrayList;
import java.util.List;

import br.Global;

public enum WeaponType {
	KNIFE(6, "knife"),
	BIG_KNIFE(7, "big knife", "knife"),
	CROWBAR(8, "crowbar"),
	MACHETE(10, "machete"),
	RUSTY_AXE(11, "rusty axe", "axe"),
	HATCHET(14, "hatchet"),
	AXE(16, "axe"),
	HEAVY_AXE(20, "heavy axe", "axe");
	
	public static int basicAttack = 4;
	
	private List<String> names = new ArrayList<String>();
	private int attackBonus;
	
	public String getName() {
		return Global.randomElement(names);
	}
	
	public int getAttackBonus() {
		return attackBonus;
	}
	
	WeaponType(int attackBonus, String... names) {
		if (names.length == 0) {
			throw new IllegalArgumentException("No names supplied.");
		}
		for (String name : names) {
			this.names.add(name);
		}
		this.attackBonus = attackBonus;
	}
	
}
