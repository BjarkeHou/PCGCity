package util;

import javax.vecmath.Tuple2d;

public class Point2i {
	public int x;
	public int y;
	
	public Point2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceTo(Point2i p) {
		return Math.sqrt(Math.abs((double)x-(double)p.x))+Math.abs((double)y-(double)p.y);
	}
}
