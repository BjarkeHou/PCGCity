package util;

import javax.vecmath.Tuple2d;

public class Point2i {
	private int x;
	private int y;
	
	public Point2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int X () {
		return x;
	}
	
	public int Y () {
		return y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceTo(Point2i p) {
		return Math.sqrt(Math.abs((double)x-(double)p.X())+Math.abs((double)y-(double)p.Y()));
	}
}
