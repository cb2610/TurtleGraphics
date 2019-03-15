import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Interpreter {
	static Paper paper = new Paper(); //I assume we only need a single paper in any one execution
	
	public Interpreter() {
		// TODO Auto-generated constructor stub
		
	}
	
	private void executeCommand(String command) { // I assume for the most part the command is well formed, otherwise we can do checks for consistency/integrity
		String[] commandParts = command.split("\\ ");
		
		try {
		
			switch(commandParts[0]) {
				case "turtle": 			//add a new turtle in the middle of the screen. I assume the name will be one word.
					paper.addTurtle(commandParts[1]);
					break;
				case "move":			//move the specified turtle forward by the specified number of units
					paper.move(commandParts[1], commandParts[2]);
					break;
				case "left": 			//rotate the specified turtle anticlockwise by the specified number of degrees	
					paper.left(commandParts[1], commandParts[2]);
					break;
				case "right": 			//rotate the specified turtle clockwise by the specified number of degrees
					paper.right(commandParts[1], commandParts[2]);
					break;
				case "pen": 			//lift the pen off or put the pen down
					if(command.endsWith("up")) {
						paper.penUp(commandParts[1]);
					}
					else if(command.endsWith("down")) {
						paper.penDown(commandParts[1]);
					}
					else {
						System.out.println("That was not a valid pen command. Please make sure you inserted a valid language command.");
					}
					break;
				case "colour": 			//set the drawing colour of the specified turtle to the specified colour
					paper.setColour(commandParts[1], commandParts[2]);
					break;
				default: 
					System.out.println("That was not a valid command. Please make sure you inserted a valid language command.");
			}
		
		
		}
		catch(Exception e) {
			System.out.println("That was not a valid command. Please make sure you inserted valid parameters in the commands. The exception is "+e.toString());
		}
	}

	public static void main(String[] args) {
		Interpreter interpreter = new Interpreter();
		
		
		Scanner s = new Scanner(System.in);
		System.out.println("Please choose either console input (c) or file input (f): "); // we allow both console input and file input
		
		String mode = s.nextLine();
		paper.setVisible(true);
		
		while (!mode.equals("c") && !mode.equals("f")) {
			System.out.println("Please insert either c or f for console / file input respectively. ");
		}
		
		if(mode.equals("c")) {
			System.out.println("Please insert input here.");
			while (s.hasNextLine()) {			
				String command = s.nextLine();
				interpreter.executeCommand(command);
				paper.setVisible(true); // I prefer setting the output to be visible here because then we can most easily see the window open while typing commands
			}
		
		}
		else if(mode.equals("f")) {
			System.out.print( "Please enter the full path of the file: " );   
		    String fileName = s.nextLine();         
		    File file = new File(fileName);             

		    if (file.exists()) {                    
				try {
					Scanner sFile = new Scanner(file);
					
			        while (sFile.hasNext()) {
			        
			            String command = sFile.nextLine();  
			            System.out.println(command);
			            interpreter.executeCommand(command);
			        }
			       
			        sFile.close();
				}
		        catch (FileNotFoundException e) {
					System.out.println("File not found");
				}
		    }
		}
		
		s.close(); 
	}
	
	

}
