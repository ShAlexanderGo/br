package br;

public final class Vector {
	private final int x;
	private final int y;
	
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	public double getLength() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double distanceTo(Vector vector) {
		return this.subtract(vector).getLength();
	}
	
	public Vector addByAngle(int length, double angle) {
		return new Vector(x + length * Math.cos(angle),	
				y + length * Math.sin(angle));
	}
	
	public Vector add(Vector vector) {
		return new Vector(x + vector.getX(), y + vector.getY());
	}
	
	public Vector subtract(Vector vector) {
		return new Vector(x - vector.getX(), y - vector.getY());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this(0, 0);
	}
	
	public Vector(Vector vector) {
		this(vector.getX(), vector.getY());
	}

	public Vector(double x, double y) {
		this((int)x, (int)y);
	}
	
}
