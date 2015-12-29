package br;

public abstract class Placeable {

	private Vector position;
	
	public double distanceTo(Placeable object) {
		return this.position.distanceTo(object.getPosition());
	}

	public Vector getPosition() {
		return position;
	}
	
	public Placeable setPosition(Vector position) {
		this.position = position;
		return this;
	}
	
	public Placeable(Vector position) {
		this.position = position;
	}
	
	public Placeable() {
		this(null);
	}
	
}
