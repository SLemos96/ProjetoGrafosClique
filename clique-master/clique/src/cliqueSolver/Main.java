package cliqueSolver;
/**
 * Main Class
 * Initializes the program and manages all of the user interaction bits
 * @author jshin13wou, jaredpetersen
 *
 */
public class Main 
{
	// Main method
	public static void main(String[] args) 
	{
		// Print out the intro message
		printIntro();
		
		// Create a new clique
		new CliqueSolver();
	}
	
	/**
	 * Print out the intro message to the application
	 */
	private static void printIntro()
	{
		System.out.println("********************************************************");
		System.out.println("Largest Complete Clique Finder\n");
		System.out.println("Created by Ethan Eiter, Joseph Shin, & Jared Petersen");
		System.out.println("********************************************************\n");
		System.out.println("Grabbing the graph.txt input file...\n");
	}

}
