package br.apps;

import java.util.HashMap;
import java.util.Map;

import br.Game;
import br.Global;

public class WinCounter {

	public static int ITS_NUMBER = 1000;
	
	public static void main(String[] args) {
		Map<String, Integer> players = new HashMap<String, Integer>();
		for (int i = 0; i < ITS_NUMBER; i++) {
			Game game = new Game();
			Global.messenger.setVerbose(false);
			String winner = game.routine();
			int count = players.containsKey(winner) ? players.get(winner) : 0;
			players.put(winner, count + 1);
		}
		for (Map.Entry<String, Integer> entry : players.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue().toString());
		}
	}
	
}
