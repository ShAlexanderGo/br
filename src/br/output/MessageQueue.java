package br.output;

import java.util.LinkedList;

public class MessageQueue {
	
	private LinkedList<String> messages = new LinkedList<String>();
	private static String lineSeparator = System.getProperty("line.separator");
	private final int LINE_LENGTH = 80;
	
	public void addMessage(String message) {
		if (!messages.isEmpty())
			if (!messages.getLast().equals(lineSeparator) 
					&& !message.equals(lineSeparator))
				message = messages.removeLast().concat(" ").concat(message);
		while (message.length() > LINE_LENGTH) {
	    	int index = LINE_LENGTH;
	    	while (message.charAt(index) != ' ') {
	    		index--;
	    	}
	    	messages.add(message.substring(0, index));
	    	messages.add(lineSeparator);
	    	message = message.substring(index+1,message.length());
	    }
		messages.add(message);
	}
	
	public boolean isEmpty() {
		return messages.isEmpty();
	}
	
	public String getFirst() {
		return messages.getFirst();
	}
	
	public String getLast() {
		return messages.getLast();
	}
	
	public String removeFirst() {
		return messages.removeFirst();
	}
	
	public String removeLast() {
		return messages.removeLast();
	}
	
}
