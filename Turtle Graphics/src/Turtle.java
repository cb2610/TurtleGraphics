import java.awt.Color;

class Turtle {
	
	int direction; // We use the direction in degrees to allow for any orientation in 360 degrees
	int x; 
	int y;
	boolean penDown;
	Color colour;
	
	public Turtle(int direction, int x, int y, boolean penDown, Color colour) {
		this.direction = direction;
		this.x = x; 
		this.y = y;
		this.penDown = penDown;
		this.colour = colour;
	}

	public void move(int newx, int newy) {
		x = newx;
		y = newy;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Color getColour() {
		return colour;
	}

	public void setColour(Color newColour) {
		this.colour = newColour;
	}

	public void rotate(int degrees) {
		direction = (direction + degrees) % 360; 		
	}

	public void setPenDown() {
		penDown = true;
	}
	
	public void setPenUp() {
		penDown = false;
	}

}
