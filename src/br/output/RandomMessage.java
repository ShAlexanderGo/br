package br.output;

import java.util.ArrayList;

import br.Global;

public class RandomMessage {

	private ArrayList<String> possibleMessages = new ArrayList<String>();
	
	public void add(String string) {
		possibleMessages.add(string);
	}
	
	public String get() {
		if (possibleMessages.size() == 0)
			return null;
		int i = Global.random.nextInt(possibleMessages.size());
		return possibleMessages.get(i);
	}
	
}
