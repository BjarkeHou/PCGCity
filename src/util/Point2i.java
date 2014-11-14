package util;

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
	
	public Point2i clone(){
		return new Point2i(x,y);
		
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Point2i)){
			return false;
		}
		Point2i p = (Point2i) o;
		if(p.x == x && p.y == y){
			return true;
		}
		return false;
	}
}
