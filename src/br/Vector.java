package br;

public class Vector {
	private int x;
	private int y;
	
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	public double getLength() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double distanceTo(Vector vector) {
		return Math.sqrt(
				Math.pow(this.getX() - vector.getX(), 2) 
				+ Math.pow(this.getY() - vector.getY(), 2));
	}
	
	public Vector addByAngle(int length, double angle) {
		x += length * Math.cos(angle);
		y += length * Math.sin(angle);
		return this;
	}
	
	public Vector add(Vector vector) {
		x += vector.getX();
		y += vector.getY();
		return this;
	}
	
	public Vector subtract(Vector vector) {
		x -= vector.getX();
		y -= vector.getY();
		return this;
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
	
}
