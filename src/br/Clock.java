package br;

public class Clock {
	public static final int MINUTES_IN_HOUR = 60;
	public static final int HOURS_IN_DAY = 24;
	
	private int ticks;
	
	public void set(int days, int hours, int minutes) {
		ticks = days * HOURS_IN_DAY * MINUTES_IN_HOUR
				+ hours * MINUTES_IN_HOUR + minutes;
	}
	
	public void step() {
		ticks++;
	}
	
	public boolean stepDown() {
		ticks--;
		if (ticks < 0) {
			ticks = 0;
			return true;
		}
		return false;
	}
	
    public Clock() {
    	ticks = 0;
    }
    
    public Clock(int days, int hours, int minutes) {
    	this.set(days, hours, minutes);
    }
    
    @Override
    public String toString() {
    	return "day " 
    			+ Integer.toString(getDays()) 
    			+ " " + String.format("%02d", getHours()) 
    			+ ":" + String.format("%02d", getMinutes());
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

}
