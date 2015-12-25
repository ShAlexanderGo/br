package br;

public class Clock {
	private final int MINUTES_IN_HOUR = 60;
	private final int HOURS_IN_DAY = 24;
	
	private int day;
	private int hour;
	private int minute;
	
	/*public void printDayAndTime() {
		Global.messenger.customMessage(this.toString(), false)
				.messageEndOfLine();
	}*/
	
	public void reset(int day, int hour, int minute) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	public void step() {
		minute++;
		hour += minute / MINUTES_IN_HOUR;
		day += hour / HOURS_IN_DAY;
		hour %= HOURS_IN_DAY;
		minute %= MINUTES_IN_HOUR;
	}
	
	public boolean stepDown() {
		minute--;
		if (minute < 0) {
			minute += MINUTES_IN_HOUR;
			hour--;
			if (hour < 0) {
				hour += HOURS_IN_DAY;
				day--;
				if (day < 0) {
					day = 0;
					hour = 0;
					minute = 0;
					return true;
				}
			}
		}
		return false;
	}
	
    public Clock() {
    	day = 0;
    	hour = 0;
    	minute = 0;
    }
    
    public Clock(int day, int hour, int minute) {
    	this.day = day;
    	this.hour = hour;
    	this.minute = minute;
    }
    
    @Override
    public String toString() {
    	return "day " 
    			+ Integer.toString(day) 
    			+ " " + String.format("%02d", hour) 
    			+ ":" + String.format("%02d", minute);
    }
    
    public int getHour() {
    	return hour;
    }
    
	public int getMinute() {
		return minute;
	}
}
