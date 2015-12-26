package br.time;

public class Clock {
	public static final int MINUTES_IN_HOUR = 60;
	public static final int HOURS_IN_DAY = 24;
	
	private int ticks;
	
	public int toTicks(int days, int hours, int minutes) {
		return days * HOURS_IN_DAY * MINUTES_IN_HOUR
				+ hours * MINUTES_IN_HOUR + minutes;
	}
	
	public void increase() {
		ticks++;
	}
	
	public void decrease() {
		ticks--;
		if (ticks < 0)
			ticks = 0;
	}
	
    public Clock() {
    	this(0, 0, 0);
    }
    
    public Clock(int days, int hours, int minutes) {
    	ticks = toTicks(days, hours, minutes);
    }
    
    public int getDays() {
    	return (ticks / HOURS_IN_DAY) / MINUTES_IN_HOUR;
    }
    
    public int getHours() {
    	return (ticks / MINUTES_IN_HOUR) % HOURS_IN_DAY;
    }
    
	public int getMinutes() {
		return ticks % MINUTES_IN_HOUR;
	}

	public int getTicks() {
		return ticks;
	}

	protected void setTicks(int ticks) {
		this.ticks = ticks;
	}
	
}
