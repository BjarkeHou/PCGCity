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
		return new Point2i(other.x-this.x, other.y-this.y);
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
	
	public Point2i GetDirUnit(){
		boolean ortho = checkOrtho();
		int xUnit = (x != 0 ? 1 : 0);
		xUnit = (x < 0 ? -xUnit : xUnit);
		int yUnit = (y != 0 ? 1 : 0);
		yUnit = (y < 0 ? -yUnit : yUnit);
		
		
		if(ortho){
			if(Math.abs(x) < Math.abs(y)){
				return new Point2i(0, yUnit);
			}
			else{
				return new Point2i(xUnit, 0);
			}
		}
		else
		{
			return new Point2i(xUnit, yUnit);
		}
	}

	private boolean checkOrtho() {
		if(Math.abs(x) < 2 * Math.abs(y)) return false;
		if(Math.abs(y) < 2 * Math.abs(x)) return false;
		return true;
	}

	public Point2i mapClamp(int limitX, int limitY) {
		int oldX = x;
		int oldY = y;
		if(oldX < 0) oldX = 0;
		if(oldY < 0) oldY = 0;
		if(oldX >= limitX) oldX = limitX-1;
		if(oldY >= limitY) oldY = limitY-1;
		return new Point2i(oldX, oldY);
		
	}
}
