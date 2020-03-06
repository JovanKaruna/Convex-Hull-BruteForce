public class Point
{
	private int x;
	private int y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int GetX(){
		return x;
	}
	
	public int GetY(){
		return y;
	}
	
	@Override
	public String toString(){
		String s = "(";
		s += x;
		s += ",";
		s += y;
		s += ")";
		return s;
	}
}