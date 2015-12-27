package br;

public class Main {
	
	public static void main(String[] args) {
		Game game = new Game();
		Global.messenger.setNonstop(true);
		game.routine();
	}

}
