package br.apps;

import br.Game;
import br.Global;

public class SimpleRun {
	
	public static void main(String[] args) {
		Game game = new Game();
		Global.messenger.setNonstop(false);
		game.routine();
	}

}
