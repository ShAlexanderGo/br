package br;

import java.util.Random;

import br.output.Messenger;

public class Global {

	public static Random random = new Random();
	public static Messenger messenger = new Messenger();
	public static Clock gameTime = new Clock(0, 5, 59) {
		@Override
		public String toString() {
			int ticks = getTicks();
			int hours = getHours();
			int minutes = getMinutes();
			String timeOfDay;
			if ((hours >= 6) && (hours < 23)) {
				timeOfDay = "Day";
			} else {
				timeOfDay = "Night";
			}
			int number = (((ticks - 6 * MINUTES_IN_HOUR) / HOURS_IN_DAY) 
					/ MINUTES_IN_HOUR) + 1;
			return timeOfDay + " " + number + " " 
					+ String.format("%02d", hours) + ":" 
					+ String.format("%02d", minutes);
		}
	};
	
}
