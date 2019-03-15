import java.util.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
//import java.awt.geom.*; //*still needed?
//import java.lang.reflect.Field;


public class Paper extends JFrame {
	
	HashMap<String,Turtle> turtleMap = new HashMap<String,Turtle>(); 	//I assume we only need one set of turtles
	
	protected Paper() {
		super("Turtle Graphics");
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screenSize.getHeight() * 2 / 3); 			//Set window size to 2/3 of screen size, for optimal viewing
		int width = (int) (screenSize.getWidth() * 2 / 3);				
		setSize(width,height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	/* Add a new turtle in the middle of the screen. I assume the name will be one word. */
	public void addTurtle(String turtleName) {
		Turtle t = new Turtle(270, this.getWidth()/2, this.getHeight()/2, true, Color.BLACK); //create a new Turtle with direction North (270 degrees)
		turtleMap.put(turtleName, t);
	}

	/* Move the specified turtle forward by the specified number of units */
	/* NOTE A very interesting extension of this would be allowing multiple different types of turtles that might handle the edge of the window differently 
	 * Some functionality that we could implement with regards to the above would be turtles that wrap around the screen (like in the game Snake), or that
	 * bounce back in the direction they came from, or that reflect back like a ray of light on a mirror.
	 * This functionality could easily be achieved by making the turtle class abstract with the method that deals with the edge behaviour abstract, and 
	 * then have multiple types of turtles implement this method and just use inheritance for the shared attributes and methods
	 * */
	public void move(String turtleName, String units) throws Exception {
		
		Turtle turtle = turtleMap.get(turtleName);
		if(turtle == null) {
			throw (new Exception("The named turtle had not been declared previously."));
		}
		
		int distance = Integer.parseInt(units); 
		
		double angle = Math.toRadians((turtle.getDirection()) % 360); 	//get the angle between the direction of the turtle and the ox axis
		int oldx = turtle.getX(); 
		int oldy = turtle.getY();
		int newx = (int) (oldx + distance * Math.cos(angle)); 
		int newy = (int) (oldy + distance * Math.sin(angle));
	
		if(turtle.penDown) { 											//only draw if the pen is down
			this.drawline(oldx, oldy, newx, newy, turtle.getColour()); 	//draw the movement - I assume the turtle moves continuously regardless of the size of the paper (does not wrap around)
		}
		
		turtle.move(newx, newy); 										//Note that if we wanted to implement multiple types of turtles we could abstract away the new coordinates inside the turtle class
	}

	/* Helper function, draws a line given the coordinates of two points and a colour */
	private void drawline(int a, int b, int c, int d, Color turtleColour) {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		
		g2d.setColor(turtleColour); 					
		g2d.drawLine(a, b, c, d);
	}
	
	/* Rotate the specified turtle anticlockwise by the specified number of degrees */
	public void left(String turtleName, String degreesString) throws Exception {
		Turtle turtle = turtleMap.get(turtleName);
		if(turtle == null) {
			throw (new Exception("The named turtle had not been declared previously."));
		}
		
		int degrees = Integer.parseInt(degreesString) % 360; 			//cycles after 360 degrees
		turtle.rotate(360-degrees); 									//rotating left is rotating right with 360 - (this number of degrees)
	}
	
	/* Rotate the specified turtle clockwise by the specified number of degrees */
	public void right(String turtleName, String degreesString) throws Exception {
		Turtle turtle = turtleMap.get(turtleName);
		if(turtle == null) {
			throw (new Exception("The named turtle had not been declared previously."));
		}
		
		int degrees = Integer.parseInt(degreesString) % 360;
		turtle.rotate(degrees);
	}

	/* Put pen down, making turtle movement cause drawing on screen */
	public void penDown(String turtleName) throws Exception {
		Turtle turtle = turtleMap.get(turtleName);
		if(turtle == null) {
			throw (new Exception("The named turtle had not been declared previously."));
		}
		
		turtle.setPenDown();
	}
	
	/* Lift pen up, making turtle movement not cause any drawing on screen */
	public void penUp(String turtleName) throws Exception {
		Turtle turtle = turtleMap.get(turtleName);
		if(turtle == null) {
			throw (new Exception("The named turtle had not been declared previously."));
		}
		
		turtle.setPenUp();
	}

	/* Change colour of turtle */
	public void setColour(String turtleName, String newColour) throws Exception {
		Turtle turtle = turtleMap.get(turtleName);
		if(turtle == null) {
			throw (new Exception("The named turtle had not been declared previously."));
		}
		
		Color colour = getColourFromName(newColour);					 //get a Color object from the String name
		turtle.setColour(colour);
	}

	/* Helper function, uses reflection to get a Color object from the string name - I assume the colour names will correspond to the Java colour names */
	private Color getColourFromName(String colour) {
		try{
			return (Color) Color.class.getField(colour.toUpperCase()).get(null);
		}
		catch (Exception e) {
			return Color.BLACK;
		}
	}
	
}
