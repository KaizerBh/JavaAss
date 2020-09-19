/**
 * 
 */
package application;

/**
 * @author Reza
 *
 */
public class Coordinates {
	
	private float x;
	private float y;
	
	public Coordinates(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x=" + x + ";" + "y=" + y;
	}
	
	

}
