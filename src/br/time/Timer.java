package br.time;

public class Timer extends Clock {

	private int startTicks;
	
	@Override
	public void step() {
		decrease();
	}
	
	public boolean isZero() {
		if (this.getTicks() == 0)
			return true;
		else
			return false;
	}
	
	public void reset() {
		setTicks(startTicks);
	}
	
	public Timer(int days, int hours, int minutes) {
		super(days, hours, minutes);
		startTicks = toTicks(days, hours, minutes);
	}
	
	public Timer() {
		super();
	}
	
}
