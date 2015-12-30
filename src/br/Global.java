package br;

import java.util.List;
import java.util.Random;

import br.output.Messenger;
import br.time.Clock;

public class Global {

	public static Random random;
	
	public static Messenger messenger;
	
	public static Clock gameTime;
	
	public static <T> T randomElement(List<? extends T> list) {
		if (list.size() == 0)
			return null;
		return list.get(random.nextInt(list.size()));
	}
	
	public static void initialize() {
		random = new Random();
		messenger = new Messenger();	
		gameTime = new Clock(0, 5, 59) {
			@Override
			public int getDays() {
				return (((getTicks() - 6 * MINUTES_IN_HOUR) / HOURS_IN_DAY) 
						/ MINUTES_IN_HOUR) + 1;
			}
			@Override
			public String toString() {
				int hours = getHours();
				int minutes = getMinutes();
				/*String timeOfDay;
				if ((hours >= 6) && (hours < 23)) {
					timeOfDay = "Day";
				} else {
					timeOfDay = "Night";
				}*/
				return "<" /*+ timeOfDay + " "*/ 
						+ String.format("%02d", getDays()) + " " 
						+ String.format("%02d", hours) + ":" 
						+ String.format("%02d", minutes) + ">";
			}
		};
	}
	
}
