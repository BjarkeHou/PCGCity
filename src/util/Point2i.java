package util;

public class Point2i {
	private int x;
	private int y;
	
	public Point2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceTo(Point2i p) {
		return Math.sqrt(Math.abs((double)x-(double)p.x))+Math.abs((double)y-(double)p.y);
	}
	
	public Point2i vecToOther(Point2i other){
		return new Point2i(this.x-other.x, this.y-other.y);
	}
	
	public Point2i clone(){
		return new Point2i(x,y);
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}
	
	public Point2i magnitude(int i){
		return new Point2i(x*i, y*i);
	}
	
	public Point2i add(Point2i other){
		return new Point2i(x+other.x(), y+other.y());
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
