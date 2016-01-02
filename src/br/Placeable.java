package br;

public abstract class Placeable {

	private Vector prevPosition;
	private Vector position;
	
	public double distanceTo(Placeable object) {
		return this.getPosition().distanceTo(object.getPosition());
	}

	public double distanceTo(int x, int y) {
		return getPosition().distanceTo(new Vector(x, y));
	}
	
	public double prevDistanceTo(Placeable object) {
		return this.getPrevPosition().distanceTo(object.getPrevPosition());
	}
	
	public boolean entered(Placeable object, int distance) {
		if ((this.getPosition() == null) || (object.getPosition() == null))
			return false;
		return this.distanceTo(object) < distance 
				&& this.prevDistanceTo(object) > distance;
	}
	
	public void move(int length, double angle) {
		setPosition(getPosition().addByAngle(length, angle));		
	}
	
	public Vector getPrevPosition() {
		return prevPosition;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Placeable setPosition(Vector position) {
		prevPosition =  this.position;
		this.position = position;
		return this;
	}
	
	public Placeable(Vector position) {
		this.prevPosition = position;
		this.position = position;
	}
	
	public Placeable() {
		this(null);
	}
	
}
