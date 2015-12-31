package br;

import java.util.List;
import java.util.Random;

import br.output.Messenger;
import br.time.Clock;

public class Global {

	public static Random random;
	
	public static Messenger messenger;
	
	public static Clock gameTime = new Clock() {
		@Override
		public void step() {
			increase();
			if (getMinutes() == 0) {
				if (getHours() == 6) {
					Global.messenger.messageDayStarts(this)
							.messageEndOfLine();
				} else if (getHours() == 23) {
					Global.messenger.messageNightStarts(this)
							.messageEndOfLine();
				}
			}
		}
		@Override
		public int getDays() {
			return (((getTicks() - 6 * MINUTES_IN_HOUR) / HOURS_IN_DAY) 
					/ MINUTES_IN_HOUR) + 1;
		}
		@Override
		public String toString() {
			int hours = getHours();
			int minutes = getMinutes();
			return "<"
					+ String.format("%02d", getDays()) + " " 
					+ String.format("%02d", hours) + ":" 
					+ String.format("%02d", minutes) + ">";
		}
		};
	
	public static <T> T randomElement(List<? extends T> list) {
		if (list.size() == 0)
			return null;
		return list.get(random.nextInt(list.size()));
	}
	
	public static void initialize() {
		random = new Random();
		messenger = new Messenger();
		gameTime.setTime(0, 5, 59);
	}
	
}
