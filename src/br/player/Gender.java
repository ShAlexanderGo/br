package br.player;

public enum Gender {
	MALE("his"),
	FEMALE("her");
	
	private final String possAdj;
	
	public String getPossAdj() {
		return possAdj;
	}
	
	Gender(String possAdj) {
		this.possAdj = possAdj;
	}
}
